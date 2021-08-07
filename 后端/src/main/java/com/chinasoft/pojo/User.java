package com.chinasoft.pojo;

public class User {
	private int uid;	//用户id 
	private String name; //用户名
	private String phoneNumber;	// 手机号
	private String password; //用户密码
	private String avatar;	// 头像
	private int fans;	// 粉丝数
	private int focus;	// 关注数
	private int getLike;	// 获得喜欢数
	private int admin;	// 身份权限
	private String address; // 收货地址
	
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int uid, String name, String phoneNumber, String password, String avatar, int fans, int focus,
			int getLike, int admin, String address) {
		super();
		this.uid = uid;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.avatar = avatar;
		this.fans = fans;
		this.focus = focus;
		this.getLike = getLike;
		this.admin = admin;
		this.address = address;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public int getFans() {
		return fans;
	}


	public void setFans(int fans) {
		this.fans = fans;
	}


	public int getFocus() {
		return focus;
	}


	public void setFocus(int focus) {
		this.focus = focus;
	}


	public int getGetLike() {
		return getLike;
	}


	public void setGetLike(int getLike) {
		this.getLike = getLike;
	}


	public int getAdmin() {
		return admin;
	}


	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	
}
