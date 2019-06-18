package com.moses.distributed.distributeLock.zkclient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
/**
 * 选主的服务
 * @author mosesji
 *
 */
public class MasterSelector {
	private ZkClient zkClient;

	private final static String MASTER_PATH = "/master"; // 需要争抢的节点
	private IZkDataListener dataListener; // 注册节点内容变化
	private UserCenter server; // 其他服务器
	private UserCenter master; // master节点
	private static boolean isRunning = false;
	
	ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);

	public MasterSelector(UserCenter server, ZkClient zkClient) {
		System.out.println(server.getMcName() + "争抢master...");
		this.server = server;
		this.zkClient = zkClient;
		this.dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				// 节点如果被删除，发起选主操作
				System.out.println("触发节点删除事件: " + arg0);
				chooseMaster();
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// 主结点变化
				
			}
		};
	}
	
	public void start() {
		//开始选举
		if(!isRunning) {
			isRunning = true;
			zkClient.subscribeDataChanges(MASTER_PATH, dataListener);		//注册节点事件
			chooseMaster();
		}
	}
	
	public void stop() {
		//停止
		if(isRunning) {
			isRunning = false;
			zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
			releaseMaster();
			executors.shutdown();
		}
	}
	
	//具体选主的逻辑
	private void chooseMaster() {
		if(!isRunning) {
			System.out.println("当前服务没有启动");
			return;
		}
		try {
			zkClient.createEphemeral(MASTER_PATH, server);
			master = server;	//把server节点赋值给master
			System.out.println(master.getMcName() + "->, 我是master.");
			
			//定时器，master释放(master出现故障)
			executors.schedule(()->{
				releaseMaster();
			}, 5, TimeUnit.SECONDS);
		} catch(ZkNodeExistsException e) {
			//表示master已经存在
			UserCenter userCenter = zkClient.readData(MASTER_PATH, true);
			if(userCenter == null) {
				chooseMaster();	//再次获取master
			} else {
				master = userCenter;
			}
		}
	}
	
	private void releaseMaster() {
		//释放锁。判断当前线程一 master才需要释放
		if(checkIfMaster()) {
			zkClient.deleteRecursive(MASTER_PATH);
		}
	}
	
	private boolean checkIfMaster() {
		//判断是否是master
		UserCenter uc = zkClient.readData(MASTER_PATH);
		if(uc.getMcName().equals(server.getMcName())) {
			master = uc;
			return true;
		}
		return false;
	}
	
	

}
