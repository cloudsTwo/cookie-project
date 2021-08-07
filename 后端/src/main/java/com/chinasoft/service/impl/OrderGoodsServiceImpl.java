package com.chinasoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.OrderGoodsMapper;
import com.chinasoft.pojo.OrderGoods;
import com.chinasoft.service.OrderGoodsService;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {

	@Autowired
	OrderGoodsMapper mapper;

	@Override
	public OrderGoods findOrderGoodsById(int id) {
		// TODO Auto-generated method stub
		return mapper.findOrderGoodsById(id);
	}

	@Override
	public OrderGoods findOrderGoodsByOrder(int oid) {
		// TODO Auto-generated method stub
		return mapper.findOrderGoodsByOrder(oid);
	}

	@Override
	public void changeOrderGoods(OrderGoods orderGoods) {
		// TODO Auto-generated method stub
		mapper.changeOrderGoods(orderGoods);
	}

	@Override
	public void addOrderGoods(OrderGoods orderGoods) {
		// TODO Auto-generated method stub
		mapper.addOrderGoods(orderGoods);
	}

	@Override
	public void deleteOrderGoodsById(int id) {
		// TODO Auto-generated method stub
		mapper.deleteOrderGoodsById(id);
	}

	@Override
	public void deleteOrderGoodsByOrder(int oid) {
		// TODO Auto-generated method stub
		mapper.deleteOrderGoodsByOrder(oid);
	}

	
}
