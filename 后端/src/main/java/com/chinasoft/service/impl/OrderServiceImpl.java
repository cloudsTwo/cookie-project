package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.OrderMapper;
import com.chinasoft.pojo.Order;
import com.chinasoft.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper mapper;

	@Override
	public Order findOrderById(int oid) {
		// TODO Auto-generated method stub
		return mapper.findOrderById(oid);
	}

	@Override
	public List<Order> findOrderByUser(int uid) {
		// TODO Auto-generated method stub
		return mapper.findOrderByUser(uid);
	}

	@Override
	public List<Order> findOrderPay(int uid,int isPay) {
		// TODO Auto-generated method stub
		return mapper.findOrderPay(uid, isPay);
	}

	@Override
	public List<Order> findOrderShip(int uid,int isShip) {
		// TODO Auto-generated method stub
		return mapper.findOrderShip(uid, isShip);
	}

	@Override
	public void changeOrder(Order order) {
		// TODO Auto-generated method stub
		mapper.changeOrder(order);
	}

	@Override
	public void changeOrderPay(int oid, int isPay) {
		// TODO Auto-generated method stub
		mapper.changeOrderPay(oid, isPay);
	}

	@Override
	public void changeOrderShip(int oid, int isShip) {
		// TODO Auto-generated method stub
		mapper.changeOrderShip(oid, isShip);
	}

	@Override
	public void addOrder(Order order) {
		// TODO Auto-generated method stub
		mapper.addOrder(order);
	}

	@Override
	public void deleteOrderById(int oid) {
		// TODO Auto-generated method stub
		mapper.deleteOrderById(oid);
	}

	@Override
	public void deleteOrderByUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteOrderByUser(uid);
	}

	@Override
	public Order findLastOrder() {
		// TODO Auto-generated method stub
		return mapper.findLastOrder();
	}

	@Override
	public List<Order> findAllOrder() {
		// TODO Auto-generated method stub
		return mapper.findAllOrder();
	}

	@Override
	public Order findOrderByOrderNum(String orderNum) {
		// TODO Auto-generated method stub
		return mapper.findOrderByOrderNum(orderNum);
	}

	
}
