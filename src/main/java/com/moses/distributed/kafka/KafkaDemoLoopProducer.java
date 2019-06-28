package com.moses.distributed.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;


public class KafkaDemoLoopProducer {
	
	private final KafkaProducer<Integer, String> producer;
	
	private final String topic = "MyTopic";	//"replica-topic"
	
	public KafkaDemoLoopProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER_LIST);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "producerDemo");
		
		//use customized partition strategy.
		//props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartition.class.getName());
		this.producer = new KafkaProducer<>(props);
	}
	
	public void sendMsg() {
		int messageNo = 0;
		for(;;) {
			String msg = "message-" + messageNo;
			producer.send(new ProducerRecord<Integer, String>(topic, messageNo, msg), new Callback() {
	
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					System.out.println("message send to: [" + metadata.partition() + "], offset:[" + metadata.offset() + "]");
				}
				
			});
			++messageNo;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		KafkaDemoLoopProducer producer = new KafkaDemoLoopProducer();
		producer.sendMsg();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
