package com.moses.distributed.distributeLock.javaapi;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class DistributeLockNativeImpl {
	//根结点
	private static final String ROOT_LOCKS = "/LOCKS";
	
	private ZooKeeper zooKeeper;
	
	private int sessionTimeout;	//会话超时时间
	
	private String lockID; 	//记录锁结点ID
	
	private final static byte[] data = {1,2};		//节点的数据
	
	private CountDownLatch latch = new CountDownLatch(1);

	public DistributeLockNativeImpl() throws IOException, InterruptedException {
		this.zooKeeper = ZookeeperClient.getInstance();
		this.sessionTimeout = ZookeeperClient.getSessionTimeOut();
	}
	
	//获取锁的方法
	public boolean lock() {
		try {
			//LOCKS/000001
			lockID = zooKeeper.create(ROOT_LOCKS+"/", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println(Thread.currentThread().getName() + " -> 成功创建了lock节点[" + lockID + "], 开始去竞争锁");
			
			List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS, true);	//获取根节点下所有子结点
			//排序，从小到大
			SortedSet<String> set = new TreeSet<String>();
			for(String child : childrenNodes) {
				set.add(ROOT_LOCKS + "/" + child);
			}
			String first = set.first();
			if(lockID.equals(first)) {
				//表示当前结点就是最小的节点
				System.out.println(Thread.currentThread().getName() + "-> 成功获得锁，lock节点为: [" + lockID + "]");
				return true;
			}
			SortedSet<String> lessThanLockId = set.headSet(lockID);
			if(!lessThanLockId.isEmpty()) {
				String prevLockId = lessThanLockId.last();	//拿到比当前LockID这个节点更小的上一个节点
				zooKeeper.exists(prevLockId, new LockerWatcher(latch));
				latch.await(sessionTimeout,TimeUnit.MILLISECONDS);
				//上面这段代码意味着如果会话超时，或者节点被删除
				System.out.println(Thread.currentThread().getName() + ", 成功获取锁：[" + lockID + "]");
			}
			return true;
			
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean unlock() {
		System.out.println(Thread.currentThread().getName() + "-> 开始释放锁: [" + lockID + "]");
		try {
			zooKeeper.delete(lockID, -1);
			System.out.println("节点[" + lockID+"]成功被删除");
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
//		CountDownLatch latch = new CountDownLatch(10);
		CyclicBarrier cb = new CyclicBarrier(10);
		Random r = new Random();
		for(int i=0; i<10; i++) {
			new Thread(()-> {
				DistributeLockNativeImpl lock = null;
				try {
					lock = new DistributeLockNativeImpl();
//					latch.countDown();
//					latch.await();
					cb.await();
					lock.lock();
					Thread.sleep(r.nextInt(500));
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if(lock != null) {
						lock.unlock();
					}
				}
			}).start();
		}
	}
}
