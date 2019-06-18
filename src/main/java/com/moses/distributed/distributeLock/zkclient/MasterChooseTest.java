package com.moses.distributed.distributeLock.zkclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class MasterChooseTest {
	private final static String CONNECTSTRING = "172.17.95.5:2181, 172.17.95.6:2181, 172.17.95.7:2181";

	public static void main(String[] args) {
		List<MasterSelector> selectors = new ArrayList<>();
		try {
			for (int i = 0; i < 10; i++) {
				ZkClient client = new ZkClient(CONNECTSTRING, 5000, 10000, new SerializableSerializer());

				UserCenter uc = new UserCenter();
				uc.setMcId(i);
				uc.setMcName("客户端" + i);

				MasterSelector selector = new MasterSelector(uc, client);
				selectors.add(selector);
				selector.start();

				TimeUnit.SECONDS.sleep(4);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			for(MasterSelector s: selectors) {
				s.stop();
			}
		}
	}
}
