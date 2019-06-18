package com.moses.distributed.distributeLock.zkclient;

import java.io.Serializable;

public class UserCenter implements Serializable {
	private int mcId;		//机器信息
	
	private String mcName;

	public int getMcId() {
		return mcId;
	}

	public void setMcId(int mcId) {
		this.mcId = mcId;
	}

	public String getMcName() {
		return mcName;
	}

	public void setMcName(String mcName) {
		this.mcName = mcName;
	}
	
	
}
