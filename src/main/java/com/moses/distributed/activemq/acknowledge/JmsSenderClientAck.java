package com.moses.distributed.activemq.acknowledge;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsSenderClientAck {
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://172.18.9.5:61616");
		
		Connection connection = null;
		
		try {
			connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//create queue (won't create if queue already exists)
			Destination destination = session.createQueue("first-queue");
			MessageProducer producer = session.createProducer(destination);
			
			for(int i = 0; i<10; i++) {
				TextMessage textMessage = session.createTextMessage("Hello, this is my message: " + i);
				producer.send(textMessage);
			}
			
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
