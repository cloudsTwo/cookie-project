package com.chinasoft.pojo;

public class Shopcart {
	int sid;
	int uid;
	int mid;
	int count;
	int status;
	String createTime;
	String updateTime;

	public Shopcart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Shopcart(int sid, int uid, int mid, int count, int status, String createTime, String updateTime) {
		super();
		this.sid = sid;
		this.uid = uid;
		this.mid = mid;
		this.count = count;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
	
}
