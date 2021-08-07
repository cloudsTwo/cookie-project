package com.chinasoft.pojo;

public class Order {
	int oid;
	String orderNum;
	int uid;
	int isPay;
	double allPay;
	String payTime;
	int isShip;
	String shipTime;
	String createTime;
	String updateTime;
	int status;
	
	
	
	public Order(int oid, String orderNum, int uid, int isPay, double allPay, String payTime, int isShip,
			String shipTime, String createTime, String updateTime, int status) {
		super();
		this.oid = oid;
		this.orderNum = orderNum;
		this.uid = uid;
		this.isPay = isPay;
		this.allPay = allPay;
		this.payTime = payTime;
		this.isShip = isShip;
		this.shipTime = shipTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}
	
	
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	public double getAllPay() {
		return allPay;
	}
	public void setAllPay(double allPay) {
		this.allPay = allPay;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getIsShip() {
		return isShip;
	}
	public void setIsShip(int isShip) {
		this.isShip = isShip;
	}
	public String getShipTime() {
		return shipTime;
	}
	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
