package com.chinasoft.mapper;

import java.util.List;

import com.chinasoft.pojo.GoodInfo;

public interface GoodInfoMapper {
	GoodInfo findGoodInfoById(int gid);
	
	GoodInfo findGoodInfoByMenu(int mid);
	
	List<GoodInfo> findAllGoodInfo();
	
	void changeGoodInfo(GoodInfo goodInfo);
	
	void addGoodInfo(GoodInfo goodInfo);
	
	void deleteGoodInfoById(int gid);
	
	void deleteGoodInfoByMenu(int mid);
}
