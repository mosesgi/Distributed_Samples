package com.moses.distributed.kafka;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;


public class KafkaDemoProducer {
	
	private final KafkaProducer<Integer, String> producer;
	
	private final String topic = "MyTopic";
	
	public KafkaDemoProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER_LIST);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "producerDemo");
		this.producer = new KafkaProducer<>(props);
	}
	
	
	
	public void sendMsg() {
		producer.send(new ProducerRecord<Integer, String>(topic, 1, "message"), new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println("message send to: [" + metadata.partition() + "], offset:[" + metadata.offset() + "]");
			}
			
		});
	}
	
	public static void main(String[] args) {
		KafkaDemoProducer producer = new KafkaDemoProducer();
		producer.sendMsg();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
