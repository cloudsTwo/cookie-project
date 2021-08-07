package com.chinasoft.pojo;

public class Comment {
	private int cid;
	private int uid;
	private int mid;
	private String ccontent;
	private String ctime;
	
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Comment(int cid, int uid, int mid, String ccontent, String ctime) {
		super();
		this.cid = cid;
		this.uid = uid;
		this.mid = mid;
		this.ccontent = ccontent;
		this.ctime = ctime;
	}


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
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


	public String getCcontent() {
		return ccontent;
	}


	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}


	public String getCtime() {
		return ctime;
	}


	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	
}
