package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Order;

public interface OrderService {
	List<Order> findAllOrder();
	
	Order findOrderById(int oid);
	
	Order findOrderByOrderNum(String orderNum);
	
	Order findLastOrder();
	
	List<Order> findOrderByUser(int uid);
	
	List<Order> findOrderPay(@Param("uid")int uid,@Param("isPay")int isPay);
	
	List<Order> findOrderShip(@Param("uid")int uid,@Param("isShip")int isShip);
	
	void changeOrder(Order order);
	
	void changeOrderPay(@Param("oid")int oid, @Param("isPay")int isPay);
	
	void changeOrderShip(@Param("oid")int oid, @Param("isShip")int isShip);
	
	void addOrder(Order order);
	
	void deleteOrderById(int oid);
	
	void deleteOrderByUser(int uid);
}
