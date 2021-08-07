package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.User;

public interface UserService {
	User LoginUser(String name,String password);

	void registered(User user);

	void changePassword(@Param("uid")int uid,@Param("password")String password);
	
	void changeAddress(@Param("uid")int uid,@Param("address")String address);
	
	void changeAvatar(@Param("uid")int uid,@Param("avatar")String avatar);
	
	void deleteUser(int uid);
	
	List<User> findAllUser();

	void changeUser(User user);

	User findOneUserById(int uid);
	
	User findOneUserByNumber(String phoneNumber);

	User findOneUserByName(String name);

	void UploadPhoto(User user);
}
