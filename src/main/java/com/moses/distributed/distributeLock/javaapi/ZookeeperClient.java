package com.moses.distributed.distributeLock.javaapi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建会话
 * @author mosesji
 *
 */
public class ZookeeperClient {
	private final static String CONNECTSTRING ="172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	private static int sessionTimeOut = 5000;
	
	public static ZooKeeper getInstance() throws IOException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, sessionTimeOut,new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				if(event.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown();
				}
			}
			
		});
		latch.await();
		return zooKeeper;
	}

	public static int getSessionTimeOut() {
		return sessionTimeOut;
	}
	
	
	
}
