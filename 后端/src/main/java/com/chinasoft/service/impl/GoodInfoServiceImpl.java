package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.GoodInfoMapper;
import com.chinasoft.pojo.GoodInfo;
import com.chinasoft.service.GoodInfoService;

@Service
public class GoodInfoServiceImpl implements GoodInfoService {

	@Autowired
	GoodInfoMapper mapper;

	@Override
	public GoodInfo findGoodInfoById(int gid) {
		// TODO Auto-generated method stub
		return mapper.findGoodInfoById(gid);
	}

	@Override
	public GoodInfo findGoodInfoByMenu(int mid) {
		// TODO Auto-generated method stub
		return mapper.findGoodInfoByMenu(mid);
	}

	@Override
	public void changeGoodInfo(GoodInfo goodInfo) {
		// TODO Auto-generated method stub
		mapper.changeGoodInfo(goodInfo);
	}

	@Override
	public void addGoodInfo(GoodInfo goodInfo) {
		// TODO Auto-generated method stub
		mapper.addGoodInfo(goodInfo);
	}

	@Override
	public void deleteGoodInfoById(int gid) {
		// TODO Auto-generated method stub
		mapper.deleteGoodInfoById(gid);
	}

	@Override
	public void deleteGoodInfoByMenu(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteGoodInfoByMenu(mid);
	}

	@Override
	public List<GoodInfo> findAllGoodInfo() {
		// TODO Auto-generated method stub
		return mapper.findAllGoodInfo();
	}

	
	
}
