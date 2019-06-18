package com.moses.distributed.distributeLock.javaapi;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class LockerWatcher implements Watcher {
	private CountDownLatch latch;
	
	public LockerWatcher(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == Event.EventType.NodeDeleted) {
			latch.countDown();
		}
	}

}
