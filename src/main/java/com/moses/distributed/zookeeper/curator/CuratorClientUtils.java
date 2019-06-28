package com.moses.distributed.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClientUtils {
	private static CuratorFramework cf;
	private final static String CONNECTSTRING ="172.18.9.5:2181, 172.18.9.6:2181, 172.18.9.7:2181";
	
	public static CuratorFramework getInstance() {
		cf = CuratorFrameworkFactory.newClient(CONNECTSTRING, 5000, 10000, new ExponentialBackoffRetry(1000, 3));
		cf.start();
		return cf;
	}
	
	public static CuratorFramework getInstanceFluentStyle() {
		cf = CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("/curator").build();
		cf.start();
		return cf;
	}
}
