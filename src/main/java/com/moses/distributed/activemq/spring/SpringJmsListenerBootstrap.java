package com.moses.distributed.activemq.spring;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJmsListenerBootstrap {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:service-jms.xml");
		System.in.read();
	}
}
