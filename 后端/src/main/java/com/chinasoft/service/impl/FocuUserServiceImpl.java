package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.FocuUserMapper;
import com.chinasoft.pojo.FocuUser;
import com.chinasoft.service.FocuUserService;

@Service
public class FocuUserServiceImpl implements FocuUserService {

	@Autowired
	FocuUserMapper mapper;

	@Override
	public FocuUser findFocuUserById(int id) {
		// TODO Auto-generated method stub
		return mapper.findFocuUserById(id);
	}

	@Override
	public List<FocuUser> findFocuUserByFocu(int uid) {
		// TODO Auto-generated method stub
		return mapper.findFocuUserByFocu(uid);
	}

	@Override
	public List<FocuUser> findFocuUserByFollow(int followId) {
		// TODO Auto-generated method stub
		return mapper.findFocuUserByFollow(followId);
	}

	@Override
	public int getFocuCount(int uid) {
		// TODO Auto-generated method stub
		return mapper.getFocuCount(uid);
	}

	@Override
	public int getFollowCount(int followId) {
		// TODO Auto-generated method stub
		return mapper.getFollowCount(followId);
	}

	@Override
	public void changeFocuUser(FocuUser focuUser) {
		// TODO Auto-generated method stub
		mapper.changeFocuUser(focuUser);
	}

	@Override
	public void addFocuUser(FocuUser focuUser) {
		// TODO Auto-generated method stub
		mapper.addFocuUser(focuUser);
	}

	@Override
	public void deleteFocuUserById(int id) {
		// TODO Auto-generated method stub
		mapper.deleteFocuUserById(id);
	}

	@Override
	public void deleteFocuUserByFocu(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteFocuUserByFocu(uid);
	}

	@Override
	public void deleteFocuUserByFollow(int followId) {
		// TODO Auto-generated method stub
		mapper.deleteFocuUserByFollow(followId);
	}

	@Override
	public FocuUser findFocuUser(int uid, int followId) {
		// TODO Auto-generated method stub
		return mapper.findFocuUser(uid, followId);
	}

	@Override
	public void deleteFocuUser(int uid, int followId) {
		// TODO Auto-generated method stub
		mapper.deleteFocuUser(uid, followId);
	}

	
}
