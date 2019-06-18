package com.moses.distributed.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HelloServer {
	public static void main(String[] args) {
		ISayHello hello;
		try {
			hello = new SayHelloImpl();
		
			LocateRegistry.createRegistry(8888);
			
			Naming.bind("rmi://localhost:8888/sayHello", hello);
			
			System.out.println("Server start success.");
		} catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
			e.printStackTrace();
		}
		
	}
}
