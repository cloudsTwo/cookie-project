package com.chinasoft.pojo;

public class GoodInfo {
	int gid;
	int mid;
	double price;
	int reserve;
	int status;
	String createTime;
	String updateTime;
	
	
	
	public GoodInfo(int gid, int mid, double price, int reserve, int status, String createTime, String updateTime) {
		super();
		this.gid = gid;
		this.mid = mid;
		this.price = price;
		this.reserve = reserve;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	
	
	public GoodInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
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
