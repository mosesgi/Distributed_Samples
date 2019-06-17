package com.moses.distributed.zookeeper.zkclient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkClientApiOperationDemo {
	
	private final static String CONNECTSTRING ="172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";
	
	private static ZkClient getInstance() {
		return new ZkClient(CONNECTSTRING, 10000);
	}
	
	public static void main(String[] args) throws Exception{
		ZkClient client = getInstance();
		
		//递归创建父节点
//		client.createPersistent("/zkclient/zk1/zk1-1/zk1-1-1", true);
		
		//递归删除节点
//		client.deleteRecursive("/zkclient");
		
		List<String> list = client.getChildren("/node");
		System.out.println(list);
		
		//watcher
		client.subscribeDataChanges("/node", new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("被删除节点名称: " + dataPath);
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("被修改节点名称: " + dataPath + " -> 修改后值: " + data);
			}
		});
		
		client.writeData("/node", "node");
		TimeUnit.SECONDS.sleep(2);
		
		client.subscribeChildChanges("/node", new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				
			}
		});
		
	}
}
