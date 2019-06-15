package com.moses.distributed.webservice;

import javax.xml.ws.Endpoint;

public class BootStrap {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/demo/hello", new ServerServiceImpl());
		//http://localhost:8888/demo/hello?wsdl
		System.out.println("publish success");
	}
}
