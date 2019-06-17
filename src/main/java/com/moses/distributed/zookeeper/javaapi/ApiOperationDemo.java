package com.moses.distributed.zookeeper.javaapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

public class ApiOperationDemo implements Watcher {
	private final static String CONNECTSTRING ="172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	private static ZooKeeper zookeeper;
	private static Stat stat = new Stat();

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zookeeper = new ZooKeeper(CONNECTSTRING, 5000, new ApiOperationDemo());
		countDownLatch.await();
//		ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("ip", "192.168.11.129"));
//		List<ACL> acls = new ArrayList<>();
//		acls.add(acl);
//        zookeeper.create("/authTest","111".getBytes(),acls,CreateMode.PERSISTENT);
//		zookeeper.getData("/authTest", true, new Stat());
		System.out.println(zookeeper.getState());

//		// 创建节点
//		String result = zookeeper.create("/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//		zookeeper.getData("/node1", new ApiOperationDemo(), stat); // 增加一个
//		System.out.println("创建成功：" + result);
//
//		// 修改数据
//		zookeeper.setData("/node1", "jmc123".getBytes(), -1);
//		Thread.sleep(2000);
//		// 修改数据
//		zookeeper.setData("/node1", "jmc234".getBytes(), -1);
//		Thread.sleep(2000);

//		// 删除节点
//		zookeeper.delete("/jmc/jmc1", -1);
//		Thread.sleep(2000);
//
//		// 创建节点和子节点
		String path = "/node";

		zookeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		TimeUnit.SECONDS.sleep(1);

		Stat stat = zookeeper.exists(path + "/node1", true);
		if (stat == null) {// 表示节点不存在
			zookeeper.create(path + "/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			TimeUnit.SECONDS.sleep(1);
		}
		// 修改子路径
		zookeeper.setData(path + "/node1", "jmc123".getBytes(), -1);
		TimeUnit.SECONDS.sleep(1);
//
//		// 获取指定节点下的子节点
//		List<String> childrens = zookeeper.getChildren("/node", true);
//		System.out.println(childrens);
	}

	@Override
	public void process(WatchedEvent watchedEvent) {
		try {
			// 如果当前的连接状态是连接成功的，那么通过计数器去控制
			if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
				if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
					countDownLatch.countDown();
					System.out.println(watchedEvent.getState() + "-->" + watchedEvent.getType());
				} else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
					System.out.println("数据变更触发.路径：" + watchedEvent.getPath() + "->改变后的值："
							+ new String(zookeeper.getData(watchedEvent.getPath(), true, stat)));
				} else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {// 子节点的数据变化会触发
					System.out.println("子节点数据变更.路径：" + watchedEvent.getPath() + "->节点的值："
							+ new String(zookeeper.getData(watchedEvent.getPath(), true, stat)));
				} else if (watchedEvent.getType() == Event.EventType.NodeCreated) {// 创建子节点的时候会触发
					System.out.println("节点创建.路径：" + watchedEvent.getPath() + "->节点的值："
							+ new String(zookeeper.getData(watchedEvent.getPath(), true, stat)));
				} else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {// 子节点删除会触发
					System.out.println("节点删除.路径：" + watchedEvent.getPath());
				}
				System.out.println(watchedEvent.getType());
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
