package com.chinasoft.pojo;

public class Mstep {
	private int stepId;
	private int stepCount;
	private String imgUrl;
	private String description;
	private int mid;
	
	
	public Mstep() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Mstep(int stepId, int stepCount, String imgUrl, String description, int mid) {
		super();
		this.stepId = stepId;
		this.stepCount = stepCount;
		this.imgUrl = imgUrl;
		this.description = description;
		this.mid = mid;
	}


	public int getStepCount() {
		return stepCount;
	}


	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMid() {
		return mid;
	}


	public void setMid(int mid) {
		this.mid = mid;
	}


	public int getStepId() {
		return stepId;
	}


	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	
	
	
}
