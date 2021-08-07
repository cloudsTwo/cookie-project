package com.chinasoft.pojo;

public class Collect {
	private int mid;
	private int uid;
	private String time;
	
	
	public Collect() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Collect(int mid, int uid, String time) {
		super();
		this.mid = mid;
		this.uid = uid;
		this.time = time;
	}


	public int getMid() {
		return mid;
	}


	public void setMid(int mid) {
		this.mid = mid;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	
}
