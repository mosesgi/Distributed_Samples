package com.moses.distributed.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloClient {

	public static void main(String[] args) {
		try {
			ISayHello hello = (ISayHello) Naming.lookup("rmi://localhost:8888/sayHello");
			System.out.println(hello);
			System.out.println(hello.sayHello("James"));
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}
}
