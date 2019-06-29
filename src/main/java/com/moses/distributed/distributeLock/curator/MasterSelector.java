package com.moses.distributed.distributeLock.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class MasterSelector {
	private final static String CONNECTSTRING ="192.168.50.201:2181,192.168.50.202:2181,192.168.50.203:2181";
	private final static String MASTER_PATH = "/curator/master/path";
	
	public static void main(String[] args) {
		CuratorFramework cf = CuratorFrameworkFactory.builder()
					.connectString(CONNECTSTRING).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		LeaderSelector leaderSelector = new LeaderSelector(cf, MASTER_PATH, new LeaderSelectorListenerAdapter() {
			
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
			}
		});
		
		leaderSelector.autoRequeue();		//自动争抢
		leaderSelector.start();				//开始选举
		
		leaderSelector.close();
	}
}
