package com.moses.distributed.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZkSessionDemo {
	private final static String CONNECTSTRING ="172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	
	public static void main(String[] args) {
		ZkClient client = new ZkClient(CONNECTSTRING, 10000);
		
		System.out.println(client + " : success.");
	}
}
