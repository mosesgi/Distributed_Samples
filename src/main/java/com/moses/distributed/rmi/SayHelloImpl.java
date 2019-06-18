package com.moses.distributed.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SayHelloImpl extends UnicastRemoteObject implements ISayHello {

	protected SayHelloImpl() throws RemoteException {
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		return "Hello from server -> name: " + name;
	}

}
