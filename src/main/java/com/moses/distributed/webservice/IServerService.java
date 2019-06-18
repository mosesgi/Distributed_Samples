package com.moses.distributed.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IServerService {
	
	@WebMethod
	String sayHello(String name);
}
