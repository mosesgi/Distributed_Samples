package com.moses.distributed.webservice;

import javax.xml.ws.Endpoint;

public class BootStrap {
	
	//JDK自带方式发布 WebService
	//运行需要先注释掉pom.xml中的Spring CXF相关包
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/demo/hello", new ServerServiceImpl());
		//http://localhost:8888/demo/hello?wsdl
		System.out.println("publish success");
	}
}
