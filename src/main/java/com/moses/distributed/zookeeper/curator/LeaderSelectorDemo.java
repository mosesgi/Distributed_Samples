package com.moses.distributed.zookeeper.curator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class LeaderSelectorDemo {
	private final static String CONNECTSTRING ="172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	private static final String MASTER_PATH = "/curator_master_path1";
	private static final int CLIENT_QTY = 10;
	
	public static void main(String[] args) {
		System.out.println("创建 " + CLIENT_QTY + " 个客户端");
		List<CuratorFramework> clients = new ArrayList<>();
		List<UseLeaderSelector> examples = new ArrayList<>();
		
		try {
			for(int i=0; i<CLIENT_QTY; i++) {
				CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECTSTRING, new ExponentialBackoffRetry(1000,3));
				clients.add(client);
				UseLeaderSelector selector = new UseLeaderSelector(client, MASTER_PATH, "Client: " + i);
				examples.add(selector);
				client.start();
				selector.start();
			}
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			for(UseLeaderSelector selector: examples) {
				CloseableUtils.closeQuietly(selector);
			}
			for(CuratorFramework client:clients) {
				CloseableUtils.closeQuietly(client);
			}
		}
	}
}
