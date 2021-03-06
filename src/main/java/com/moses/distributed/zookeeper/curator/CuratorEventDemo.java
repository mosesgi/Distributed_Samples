package com.moses.distributed.zookeeper.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

public class CuratorEventDemo {
	/**
     * 三种watcher来做节点的监听
     * pathcache   监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache   监视一个节点的创建、更新、删除
     * TreeCache   pathcaceh+nodecache 的合体（监视路径下的创建、更新、删除事件），
     * 缓存路径下的所有子节点的数据
     */
	
	public static void main(String[] args) throws Exception{
		CuratorFramework cf = CuratorClientUtils.getInstance();
		
		//节点变化  NodeCache
		NodeCache cache = new NodeCache(cf, "/curator", false);
		cache.start(true);
		cache.getListenable().addListener(() ->{
			System.out.println("节点数据发生变化，变化后的结果: " + new String(cache.getCurrentData().getData()));
		});
		cf.setData().forPath("/curator", "jmc".getBytes());
		
		
		//Path children cache
		PathChildrenCache pCache = new PathChildrenCache(cf, "/event", true);
		pCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
		//Normal / BUILD_INITIAL_CACHE / POST_INITIALIZED_EVENT
		pCache.getListenable().addListener((cf1, event) -> {
			switch(event.getType()) {
				case CHILD_ADDED:
					System.out.println("增加子节点.");
					break;
				case CHILD_REMOVED:
					System.out.println("删除子节点.");
					break;
				case CHILD_UPDATED:
					System.out.println("更新子节点.");
					break;
				default:
					break;
			}
		});
		
		cf.create().withMode(CreateMode.PERSISTENT).forPath("/event", "event".getBytes());
		TimeUnit.SECONDS.sleep(1);
		
		cf.create().withMode(CreateMode.EPHEMERAL).forPath("/event/e1", "1".getBytes());
		TimeUnit.SECONDS.sleep(1);
		
		cf.setData().forPath("/event/e1", "12".getBytes());
		TimeUnit.SECONDS.sleep(1);
		
		cf.delete().forPath("/event/e1");
		TimeUnit.SECONDS.sleep(1);
	}
}
