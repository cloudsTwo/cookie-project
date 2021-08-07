package com.chinasoft.pojo;

public class Like {
	private int uid;
	private int mid;
	private String time;
	
	public Like() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Like(int uid, int mid, String time) {
		super();
		this.uid = uid;
		this.mid = mid;
		this.time = time;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
