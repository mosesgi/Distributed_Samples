package com.moses.distributed.activemq.javaBroker;

import org.apache.activemq.broker.BrokerService;

public class DefineBrokerServer {
	public static void main(String[] args) {
		BrokerService brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		try {
			brokerService.addConnector("tcp://localhost:61616");
			brokerService.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
