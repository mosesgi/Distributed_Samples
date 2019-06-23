package com.moses.distributed.zookeeper.curator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class UseLeaderSelector extends LeaderSelectorListenerAdapter implements Closeable{
	private final String name;
	private final LeaderSelector leaderSelector;
	private final AtomicInteger leaderCount = new AtomicInteger();
	
	

	public UseLeaderSelector(CuratorFramework client, String path, String name) {
		this.name = name;
		this.leaderSelector = new LeaderSelector(client, path, this);
		leaderSelector.autoRequeue();
	}
	
	public void start() {
		leaderSelector.start();
	}

	@Override
	public void takeLeadership(CuratorFramework client) throws Exception {
		final int waitSec = new Random().nextInt(50);
		System.out.println(name + " -> 我现在是leader, 等待时间：" + waitSec + "s, 抢到领导的次数：" + leaderCount.getAndIncrement());
		
//		TimeUnit.SECONDS.toMillis(1000);
	}

	@Override
	public void close() throws IOException {
		leaderSelector.close();
	}

}
