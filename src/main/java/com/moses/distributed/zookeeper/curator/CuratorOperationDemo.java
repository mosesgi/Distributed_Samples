package com.moses.distributed.zookeeper.curator;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.CreateMode;

public class CuratorOperationDemo {
	
	public void demoCode() throws Exception {
		CuratorFramework cf = CuratorClientUtils.getInstance();
		System.out.println("连接成功!");
		System.out.println("Current Thread name: " + Thread.currentThread().getName());
		
		//创建节点
//		String result = cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curator/c1/c11", "123".getBytes());
//		System.out.println(result);
		
		//删除节点
//		cf.delete().deletingChildrenIfNeeded().forPath("/node");
		
		//查询
//		Stat stat = new Stat();
//		byte[] bytes = cf.getData().storingStatIn(stat).forPath("/curator");
//		System.out.println("查询结果: " + new String(bytes) + ", stat: " + stat);
		
		//更新
//		stat = cf.setData().forPath("/curator", "123".getBytes());
//		System.out.println(stat);
		
		//异步操作
		CountDownLatch latch = new CountDownLatch(1);
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
			.inBackground(new BackgroundCallback() {

				@Override
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					System.out.println(Thread.currentThread().getName() + " -> resultCode: " + event.getResultCode() + " -> " + event.getType());
					latch.countDown();
				}
				
			}).forPath("/moses", "123".getBytes());
		latch.await();
		
		//事务操作（Curator独有）
		Collection<CuratorTransactionResult> resultCol = cf.inTransaction().create().forPath("/trans", "111".getBytes())
				.and().setData().forPath("/abc", "111".getBytes()).and().commit();
		for(CuratorTransactionResult result : resultCol) {
			System.out.println(result.getForPath() + " -> " + result.getType());
		}
	}
	
	public void deletePath() throws Exception {
		CuratorFramework cf = CuratorClientUtils.getInstance();
		cf.delete().deletingChildrenIfNeeded().forPath("/dubbo");
	}
	
	public static void main(String[] args) throws Exception{
//		new CuratorOperationDemo().demoCode();
		
		new CuratorOperationDemo().deletePath();
	}
}
