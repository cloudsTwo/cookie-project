package com.chinasoft.pojo;

public class Mmaterial {
	private int mtId;
	private String materialName;
	private String mamount;
	private int mid;
	
	
	public Mmaterial() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Mmaterial(String materialName, String mamount,int mid, int mtId) {
		super();
		this.materialName = materialName;
		this.mamount = mamount;
		this.mid = mid;
		this.mtId = mtId;
	}


	public String getMaterialName() {
		return materialName;
	}


	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}


	public String getMamount() {
		return mamount;
	}


	public void setMamount(String mamount) {
		this.mamount = mamount;
	}


	public int getMid() {
		return mid;
	}


	public void setMid(int mid) {
		this.mid = mid;
	}


	public int getMtId() {
		return mtId;
	}


	public void setMtId(int mtId) {
		this.mtId = mtId;
	}

	
}
