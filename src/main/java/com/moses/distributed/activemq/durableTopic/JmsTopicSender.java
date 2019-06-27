package com.moses.distributed.activemq.durableTopic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsTopicSender {
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://172.18.9.5:61616");
		
		Connection connection = null;
		
		try {
			connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			//create topic instead of createQueue
			Destination destination = session.createTopic("first-topic");
			MessageProducer producer = session.createProducer(destination);
			
			TextMessage textMessage = session.createTextMessage("Hello, this is my first message");
			textMessage.setStringProperty("key", "12345");
			producer.send(textMessage);
			
			session.commit();
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
