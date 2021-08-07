package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Shopcart;

public interface ShopcartService {
	
	Shopcart findShopcartById(int sid);
	
	Shopcart findShopcart(@Param("uid")int uid, @Param("mid")int mid);
	
	List<Shopcart> findShopcartByStatus(@Param("uid")int uid, @Param("status")int status);
	
	List<Shopcart> findShopcartByUser(int uid);
	
	List<Shopcart> findShopcartByMenu(int mid);
	
	void changeShopcart(Shopcart shopcart);
	
	void addShopcart(Shopcart shopcart);
	
	void deleteShopcartById(int sid);
	
	void deleteShopcartByUser(int uid);
}
