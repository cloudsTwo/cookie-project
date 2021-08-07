package com.chinasoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.LikeMapper;
import com.chinasoft.pojo.Like;
import com.chinasoft.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	LikeMapper mapper;

	@Override
	public Like findLike(int uid, int mid) {
		// TODO Auto-generated method stub
		return mapper.findLike(uid, mid);
	}

	@Override
	public void addLike(Like like) {
		// TODO Auto-generated method stub
		mapper.addLike(like);
	}

	@Override
	public void deleteLike(int uid, int mid) {
		// TODO Auto-generated method stub
		mapper.deleteLike(uid, mid);
	}
}
