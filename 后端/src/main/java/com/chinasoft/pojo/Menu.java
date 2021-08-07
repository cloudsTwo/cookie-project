package com.chinasoft.pojo;

public class Menu {
	private int mid;
	private int uid;
	private String mname;
	private String reportTime;
	private String description;
	private String desImg;
	private String tips;
	private String prepareTime;
	private String cookieTime;
	private String flavour;
	private int hard;
	private Boolean isSelf;
	private int collectCount;
	private int likeCount;
	private int views;
	private int isChecked;
	
	
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Menu(int mid, int uid, String mName, String reportTime, String description, String desImg, String tips,
			String prepareTime, String cookieTime, String flavour, int hard, Boolean isSelf, int colletCount,
			int likeCount, int views, int isChecked) {
		super();
		this.mid = mid;
		this.uid = uid;
		this.mname = mName;
		this.reportTime = reportTime;
		this.description = description;
		this.desImg = desImg;
		this.tips = tips;
		this.prepareTime = prepareTime;
		this.cookieTime = cookieTime;
		this.flavour = flavour;
		this.hard = hard;
		this.isSelf = isSelf;
		this.collectCount = colletCount;
		this.likeCount = likeCount;
		this.views = views;
		this.isChecked = isChecked;
	}
	
	


	public int getIsChecked() {
		return isChecked;
	}


	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
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


	public String getMname() {
		return mname;
	}


	public void setMname(String mName) {
		this.mname = mName;
	}


	public String getReportTime() {
		return reportTime;
	}


	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDesImg() {
		return desImg;
	}


	public void setDesImg(String desImg) {
		this.desImg = desImg;
	}


	public String getTips() {
		return tips;
	}


	public void setTips(String tips) {
		this.tips = tips;
	}


	public String getPrepareTime() {
		return prepareTime;
	}


	public void setPrepareTime(String prepareTime) {
		this.prepareTime = prepareTime;
	}


	public String getCookieTime() {
		return cookieTime;
	}


	public void setCookieTime(String cookieTime) {
		this.cookieTime = cookieTime;
	}


	public String getFlavour() {
		return flavour;
	}


	public void setFlavour(String flavour) {
		this.flavour = flavour;
	}


	public int getHard() {
		return hard;
	}


	public void setHard(int hard) {
		this.hard = hard;
	}


	public Boolean getIsSelf() {
		return isSelf;
	}


	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}


	public int getCollectCount() {
		return collectCount;
	}


	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}


	public int getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}


	public int getViews() {
		return views;
	}


	public void setViews(int views) {
		this.views = views;
	}
	
}
