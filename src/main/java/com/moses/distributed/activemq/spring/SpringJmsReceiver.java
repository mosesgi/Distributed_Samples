package com.moses.distributed.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringJmsReceiver {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:service-jms.xml");
		JmsTemplate jmsTemplate = (JmsTemplate)context.getBean("jmsTemplate");
		String msg = (String)jmsTemplate.receiveAndConvert();
		System.out.println(msg);
		
		context.close();
	}
}
