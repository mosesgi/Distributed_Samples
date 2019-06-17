package com.moses.distributed.zookeeper.javaapi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CreateSessionDemo {
	private final static String CONNECT_STR = "172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	private static CountDownLatch latch = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(CONNECT_STR, 5000, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				//如果当前连接状态是连接成功的，那么通过计数器控制
				if(event.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown();
				}
				System.out.println(event.getState());
			}
			
		});
		latch.await();
		System.out.println(zk.getState());
		
	}
}
