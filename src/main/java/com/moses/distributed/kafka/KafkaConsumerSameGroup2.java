package com.moses.distributed.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import kafka.utils.ShutdownableThread;

public class KafkaConsumerSameGroup2 extends ShutdownableThread{
	//High level consumer
	//Low level consumer
	
	private final KafkaConsumer<Integer, String> consumer;
	
	public KafkaConsumerSameGroup2() {
		super("KafkaConsumerDemo", false);
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER_LIST);
		//GroupId
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoGroup1");
		//是否自动提交消息
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		//自动提交的间隔时间
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		//设置使用最开始的offset偏移量为当前GroupID的最早消息
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//设置心跳时间
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		//key/value反序列化对象
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		
		this.consumer = new KafkaConsumer<Integer, String>(props);
	}

	@Override
	public void doWork() {
		consumer.subscribe(Collections.singleton("replica-topic"));
		ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
		for(ConsumerRecord<Integer, String> record: records) {
			System.out.println("[" + record.partition() + "] receiver message: [" + record.key() + " -> " + record.value() + "], offset:" + record.offset());
		}
	}
	
	public static void main(String[] args) {
		KafkaConsumerSameGroup2 consumer = new KafkaConsumerSameGroup2();
		consumer.start();
	}
}
