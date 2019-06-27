package com.moses.distributed.activemq.durableTopic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsTopicPersistentReceiver {
	
	//持久化订阅步骤：
	//1. 先运行一次Receiver, 注册上clientID。关闭Receiver
	//2. 运行Sender，Sender无需ClientID
	//3. 再运行Receiver, 可接收到消息
	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://172.18.9.5:61616");

		Connection connection = null;

		try {
			connection = factory.createConnection();
			connection.setClientID("specific_client");		//持久化订阅
			connection.start();

			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

			// create queue (won't create if queue already exists)
			Topic topic = session.createTopic("first-topic");
			
			MessageConsumer consumer = session.createDurableSubscriber(topic, "specific_client");	//持久化订阅

			TextMessage textMessage = (TextMessage)consumer.receive();
			System.out.println(textMessage.getText());
			System.out.println(textMessage.getStringProperty("key"));

			session.commit();
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
