package com.chinasoft.pojo;

public class FocuUser {
	int id;
	int uid;
	int followId;
	String time;
	
	public FocuUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FocuUser(int uid, int followId, String time) {
		super();
		this.uid = uid;
		this.followId = followId;
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getFollowId() {
		return followId;
	}
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
