package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.CollectMapper;
import com.chinasoft.pojo.Collect;
import com.chinasoft.service.CollectService;

@Service
public class CollectServiceImpl implements CollectService{
	@Autowired
	CollectMapper mapper;

	@Override
	public Collect findCollect(int uid, int mid) {
		// TODO Auto-generated method stub
		return mapper.findCollect(uid, mid);
	}

	@Override
	public void addCollect(Collect collect) {
		// TODO Auto-generated method stub
		mapper.addCollect(collect);
	}

	@Override
	public void deleteCollect(int uid, int mid) {
		// TODO Auto-generated method stub
		mapper.deleteCollect(uid, mid);
	}

	@Override
	public List<Collect> findCollectByUser(int uid) {
		// TODO Auto-generated method stub
		return mapper.findCollectByUser(uid);
	}

	@Override
	public void deleteCollects(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteCollects(mid);
	}

	@Override
	public void deleteCollectByUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteCollectByUser(uid);
	}
}
