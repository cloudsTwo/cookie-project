package com.chinasoft.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.OrderGoods;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.OrderGoodsService;

@Controller
@RequestMapping("orderGoods")
@CrossOrigin
public class OrderGoodsController {

	@Autowired
	OrderGoodsService service;
	@Autowired
	MenuService menuService;
	
	@UserLoginToken
	@RequestMapping("findOrderGoodsById")
	@ResponseBody
	public OrderGoods findOrderGoodsById(int id) {
		
		return service.findOrderGoodsById(id);
	}
	
	@UserLoginToken
	@RequestMapping("findOrderGoodsByOrder")
	@ResponseBody
	public OrderGoods findOrderGoodsByOrder(Integer oid) {
		return service.findOrderGoodsByOrder(oid);
	}
	
	
	@UserLoginToken
	@RequestMapping(value = "/addOrderGoods", method=RequestMethod.POST)
	@ResponseBody
	public int addOrderGoods(@RequestBody OrderGoods orderGoods) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    orderGoods.setCreateTime(date);
	    
		service.addOrderGoods(orderGoods);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeOrderGoods", method=RequestMethod.POST)
	@ResponseBody
	public int changeOrderGoods(@RequestBody OrderGoods orderGoods) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    orderGoods.setUpdateTime(date);
	    
		service.changeOrderGoods(orderGoods);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteOrderGoodsById")
	@ResponseBody
	public int deleteOrderGoodsById(int id) {
		int flag = 1;
		service.deleteOrderGoodsById(id);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteOrderGoodsByUser")
	@ResponseBody
	public int deleteShopcartByUser(int oid) {
		int flag = 1;
		service.deleteOrderGoodsByOrder(oid);
		
		return flag;
	}
}
