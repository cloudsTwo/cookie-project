package com.chinasoft.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.Order;
import com.chinasoft.pojo.OrderGoods;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.MmaterialService;
import com.chinasoft.service.OrderGoodsService;
import com.chinasoft.service.OrderService;

@Controller
@RequestMapping("order")
@CrossOrigin
public class OrderController {

	@Autowired
	OrderService service;
	@Autowired
	MenuService menuService;
	@Autowired
	OrderGoodsService orderGoodsService;
	@Autowired
	MmaterialService mmaterialService;
	
	@UserLoginToken
	@RequestMapping("findOrderById")
	@ResponseBody
	public Map<String,Object> findOrderById(int oid) {
		
		Order order = service.findOrderById(oid);
		Map<String,Object> hm = new HashMap<>();
		hm.put("order", order);
		
		OrderGoods og = orderGoodsService.findOrderGoodsByOrder(order.getOid());
		hm.put("orderGood", og);
		
		Menu menu = menuService.findMenuById(og.getMid());
		hm.put("menu", menu);
		
		hm.put("material", mmaterialService.findMmaterial(menu.getMid()));
		
		return hm;
	}
	
	@UserLoginToken
	@RequestMapping("findOrderByOrderNum")
	@ResponseBody
	public Map<String,Object> findOrderByOrderNum(String orderNum) {
		
		Order order = service.findOrderByOrderNum(orderNum);
		Map<String,Object> hm = new HashMap<>();
		hm.put("order", order);
		
		OrderGoods og = orderGoodsService.findOrderGoodsByOrder(order.getOid());
		hm.put("orderGood", og);
		
		Menu menu = menuService.findMenuById(og.getMid());
		hm.put("menu", menu);
		
		hm.put("material", mmaterialService.findMmaterial(menu.getMid()));
		
		return hm;
	}
	
	@UserLoginToken
	@RequestMapping("findAllOrder")
	@ResponseBody
	public Object[] findAllOrder() {
		List<Order> orders = service.findAllOrder();
		int length = orders.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < length; i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("order", orders.get(i));
			
			OrderGoods og = orderGoodsService.findOrderGoodsByOrder(orders.get(i).getOid());
			hm.put("orderGood", og);
			
			Menu menu = menuService.findMenuById(og.getMid());
			hm.put("menu", menu);
			
			hm.put("material", mmaterialService.findMmaterial(menu.getMid()));
			
			array[i] = hm;
		}
		
		return array;
	}
	
	@UserLoginToken
	@RequestMapping("findOrderByUser")
	@ResponseBody
	public Object[] findOrderByUser(int uid) {
		List<Order> orders = service.findOrderByUser(uid);
		int length = orders.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < length; i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("order", orders.get(i));
			
			OrderGoods og = orderGoodsService.findOrderGoodsByOrder(orders.get(i).getOid());
			hm.put("orderGood", og);
			
			Menu menu = menuService.findMenuById(og.getMid());
			hm.put("menu", menu);
			
			hm.put("material", mmaterialService.findMmaterial(menu.getMid()));
			
			array[i] = hm;
		}
		
		return array;
	}
	
	@UserLoginToken
	@RequestMapping("findOrderPay")
	@ResponseBody
	public List<Order> findOrderPay(int uid, Integer isPay) {
		return service.findOrderPay(uid,isPay);
	}
	
	@UserLoginToken
	@RequestMapping("findOrderShip")
	@ResponseBody
	public List<Order> findOrderShip(int uid, Integer isShip) {
		return service.findOrderShip(uid,isShip);
	}
	
	
	@UserLoginToken
	@RequestMapping(value = "/addOrder", method=RequestMethod.POST)
	@ResponseBody
	public int addOrder(@RequestBody Order order) {
		System.out.println(order.getAllPay() + ":" + order.getUpdateTime());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    order.setCreateTime(date);
	    
		service.addOrder(order);
		Order newo = service.findLastOrder();
		
		return newo.getOid();
	}
	
	// 查找最新
	@RequestMapping("findLastOrder")
	@ResponseBody
	public Order findLastOrder() {
		
		return service.findLastOrder();
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeOrder", method=RequestMethod.POST)
	@ResponseBody
	public int changeOrder(@RequestBody Order order) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    order.setUpdateTime(date);
	    
		service.changeOrder(order);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("/changeOrderPay")
	@ResponseBody
	public int changeOrderPay(int oid, int isPay) {
		int flag = 1;
		Order order = service.findOrderById(oid);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    order.setUpdateTime(date);
	    order.setIsPay(isPay);
		
		service.changeOrder(order);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("/changeOrderShip")
	@ResponseBody
	public int changeOrderShip(int oid, int isShip) {
		int flag = 1;
		Order order = service.findOrderById(oid);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    order.setUpdateTime(date);
	    order.setIsShip(isShip);
		
		service.changeOrder(order);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteOrderById")
	@ResponseBody
	public int deleteOrderById(int oid) {
		int flag = 1;
		service.deleteOrderById(oid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteOrderByUser")
	@ResponseBody
	public int deleteOrderByUser(int uid) {
		int flag = 1;
		service.deleteOrderByUser(uid);
		
		return flag;
	}
}
