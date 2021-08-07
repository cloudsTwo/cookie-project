package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Collect;

public interface CollectService {
	Collect findCollect(@Param("uid")int uid, @Param("mid")int mid);
	
	List<Collect> findCollectByUser(int uid);
	
	void addCollect(Collect collect);
	
	void deleteCollect(@Param("uid")int uid, @Param("mid")int mid);
	
	void deleteCollects(int mid);
	
	void deleteCollectByUser(int uid);
}
