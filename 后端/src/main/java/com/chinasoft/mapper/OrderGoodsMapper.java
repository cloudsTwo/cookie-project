package com.chinasoft.mapper;

import com.chinasoft.pojo.OrderGoods;

public interface OrderGoodsMapper {
	OrderGoods findOrderGoodsById(int id);
	
	OrderGoods findOrderGoodsByOrder(int oid);
	
	void changeOrderGoods(OrderGoods orderGoods);
	
	void addOrderGoods(OrderGoods orderGoods);
	
	void deleteOrderGoodsById(int id);
	
	void deleteOrderGoodsByOrder(int oid);
}
