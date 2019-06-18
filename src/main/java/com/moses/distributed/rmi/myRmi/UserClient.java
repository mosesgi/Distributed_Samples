package com.moses.distributed.rmi.myRmi;

import java.io.IOException;
import java.net.UnknownHostException;

public class UserClient {
	
	public static void main(String[] args) throws IOException {
		User user = new User_Stub();
		int age = user.getAge();
		
		System.out.println(age);
	}
}
