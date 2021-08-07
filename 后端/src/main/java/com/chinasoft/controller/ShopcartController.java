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
import com.chinasoft.pojo.Shopcart;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.MmaterialService;
import com.chinasoft.service.ShopcartService;
@Controller
@RequestMapping("shopcart")
@CrossOrigin
public class ShopcartController {

	@Autowired
	ShopcartService service;
	@Autowired
	MenuService menuService;
	@Autowired
	MmaterialService mmaterialService;
	
	@UserLoginToken
	@RequestMapping("findShopcartById")
	@ResponseBody
	public Shopcart findShopcartById(int sid) {
		
		return service.findShopcartById(sid);
	}
	
	@UserLoginToken
	@RequestMapping("findShopcart")
	@ResponseBody
	public Shopcart findShopcart(int uid,int mid) {
		
		return service.findShopcart(uid,mid);
	}
	
	@UserLoginToken
	@RequestMapping("findShopcartByUser")
	@ResponseBody
	public Object[] findShopcartByUser(Integer uid) {
		List<Shopcart> shopcarts = service.findShopcartByUser(uid);
		int length = shopcarts.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < length; i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("shopcart", shopcarts.get(i));
			hm.put("menu", menuService.findMenuById(shopcarts.get(i).getMid()));
			hm.put("material", mmaterialService.findMmaterial(shopcarts.get(i).getMid()));
			array[i] = hm;
		}
		
		return array;
	}
	
	@UserLoginToken
	@RequestMapping("findShopcartByStatus")
	@ResponseBody
	public Object[] findShopcartByStatus(int uid, Integer status) {
		List<Shopcart> shopcarts = service.findShopcartByStatus(uid, status);
		int length = shopcarts.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < length; i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("shopcart", shopcarts.get(i));
			hm.put("menu", menuService.findMenuById(shopcarts.get(i).getMid()));
			hm.put("material", mmaterialService.findMmaterial(shopcarts.get(i).getMid()));
			array[i] = hm;
		}
		
		return array;
	}
	
	
	@UserLoginToken
	@RequestMapping(value = "/addShopcart", method=RequestMethod.POST)
	@ResponseBody
	public int addShopcart(@RequestBody Shopcart shopcart) {
		System.out.println(shopcart.getMid()+":"+shopcart.getUpdateTime());
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    shopcart.setCreateTime(date);
	    
		service.addShopcart(shopcart);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeShopcart", method=RequestMethod.POST)
	@ResponseBody
	public int changeShopcart(@RequestBody Shopcart shopcart) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    shopcart.setUpdateTime(date);
	    
		service.changeShopcart(shopcart);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteShopcartById")
	@ResponseBody
	public int deleteShopcartById(int sid) {
		int flag = 1;
		service.deleteShopcartById(sid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteShopcartByUser")
	@ResponseBody
	public int deleteShopcartByUser(int uid) {
		int flag = 1;
		service.deleteShopcartByUser(uid);
		
		return flag;
	}
}
