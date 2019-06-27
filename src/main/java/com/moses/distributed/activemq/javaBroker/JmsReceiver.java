package com.moses.distributed.activemq.javaBroker;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsReceiver {
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		Connection connection = null;

		try {
			connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

			// create queue (won't create if queue already exists)
			Destination destination = session.createQueue("first-queue");
			MessageConsumer consumer = session.createConsumer(destination);

			TextMessage textMessage = (TextMessage)consumer.receive();
			System.out.println(textMessage.getText());
			System.out.println(textMessage.getStringProperty("key"));

			session.commit();		//createSesssion(true)时，如果不commit, 消息会一直存在
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
