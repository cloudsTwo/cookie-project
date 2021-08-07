package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.ShopcartMapper;
import com.chinasoft.pojo.Shopcart;
import com.chinasoft.service.ShopcartService;

@Service
public class ShopcartServiceImpl implements ShopcartService{
	
	@Autowired
	ShopcartMapper mapper;

	@Override
	public Shopcart findShopcartById(int sid) {
		// TODO Auto-generated method stub
		return mapper.findShopcartById(sid);
	}

	@Override
	public List<Shopcart> findShopcartByUser(int uid) {
		// TODO Auto-generated method stub
		return mapper.findShopcartByUser(uid);
	}

	@Override
	public void changeShopcart(Shopcart shopcart) {
		// TODO Auto-generated method stub
		mapper.changeShopcart(shopcart);
	}

	@Override
	public void addShopcart(Shopcart shopcart) {
		// TODO Auto-generated method stub
		mapper.addShopcart(shopcart);
	}

	@Override
	public void deleteShopcartById(int sid) {
		// TODO Auto-generated method stub
		mapper.deleteShopcartById(sid);
	}

	@Override
	public void deleteShopcartByUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteShopcartByUser(uid);
	}

	@Override
	public List<Shopcart> findShopcartByStatus(int uid, int status) {
		// TODO Auto-generated method stub
		return mapper.findShopcartByStatus(uid, status);
	}

	@Override
	public Shopcart findShopcart(int uid, int mid) {
		// TODO Auto-generated method stub
		return mapper.findShopcart(uid, mid);
	}

	@Override
	public List<Shopcart> findShopcartByMenu(int mid) {
		// TODO Auto-generated method stub
		return mapper.findShopcartByMenu(mid);
	}

	
}
