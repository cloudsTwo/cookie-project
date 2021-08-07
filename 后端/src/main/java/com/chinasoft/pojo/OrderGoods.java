package com.chinasoft.pojo;

public class OrderGoods {
	int id;
	int oid;
	int mid;
	int count;
	double singlePrice;
	String address;
	int status;
	String createTime;
	String updateTime;
	String userName;
	String phoneNumber;
	
	public OrderGoods() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderGoods(int id, int oid, int mid, int count, double singlePrice, String address, int status,
			String createTime, String updateTime, String userName, String phoneNumber) {
		super();
		this.id = id;
		this.oid = oid;
		this.mid = mid;
		this.count = count;
		this.singlePrice = singlePrice;
		this.address = address;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int orderId) {
		this.oid = orderId;
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
	public double getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(double singlePrice) {
		this.singlePrice = singlePrice;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
