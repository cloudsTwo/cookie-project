package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.GoodInfo;

public interface GoodInfoService {
	
	GoodInfo findGoodInfoById(int gid);
	
	GoodInfo findGoodInfoByMenu(int mid);
	
	List<GoodInfo> findAllGoodInfo();
	
	void changeGoodInfo(GoodInfo goodInfo);
	
	void addGoodInfo(GoodInfo goodInfo);
	
	void deleteGoodInfoById(int gid);
	
	void deleteGoodInfoByMenu(int mid);
}
