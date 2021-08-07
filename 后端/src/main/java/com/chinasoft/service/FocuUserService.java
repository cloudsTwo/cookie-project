package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.FocuUser;

public interface FocuUserService {
	
	FocuUser findFocuUserById(int id);
	
	FocuUser findFocuUser(@Param("uid")int uid, @Param("followId")int followId);
	
	List<FocuUser> findFocuUserByFocu(int uid);
	
	List<FocuUser> findFocuUserByFollow(int followId);
	
	int getFocuCount(int uid);
	
	int getFollowCount(int followId);
	
	void changeFocuUser(FocuUser focuUser);
	
	void addFocuUser(FocuUser focuUser);
	
	void deleteFocuUser(@Param("uid")int uid, @Param("followId")int followId);
	
	void deleteFocuUserById(int id);
	
	void deleteFocuUserByFocu(int uid);
	
	void deleteFocuUserByFollow(int followId);
}
