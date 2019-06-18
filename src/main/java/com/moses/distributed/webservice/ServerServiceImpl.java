package com.moses.distributed.webservice;

import javax.jws.WebService;

@WebService
public class ServerServiceImpl implements IServerService {

	@Override
	public String sayHello(String name) {
		System.out.println("call sayHello()");
		return "Server returned. Hello, " + name;
	}
}
