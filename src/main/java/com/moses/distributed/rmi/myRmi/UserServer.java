package com.moses.distributed.rmi.myRmi;

public class UserServer extends User{
	
	
	public static void main(String[] args) {
		UserServer userServer = new UserServer();
		userServer.setAge(18);
		
		User_Skeleton skel = new User_Skeleton(userServer);
		
		skel.start();
	}
}
