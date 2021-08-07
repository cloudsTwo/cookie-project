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
import com.chinasoft.pojo.GoodInfo;
import com.chinasoft.pojo.Menu;
import com.chinasoft.service.GoodInfoService;
import com.chinasoft.service.MenuService;

@Controller
@RequestMapping("goodInfo")
@CrossOrigin
public class GoodInfoController {

	@Autowired
	GoodInfoService service;
	@Autowired
	MenuService menuService;
	
	@UserLoginToken
	@RequestMapping("findAllGoodInfo")
	@ResponseBody
	public Object[] findAllGoodInfo() {
		
		List<GoodInfo> gis = service.findAllGoodInfo();
		int length = gis.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < length; i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("goodInfo", gis.get(i));
			
			Menu menu = menuService.findMenuById(gis.get(i).getMid());
			hm.put("menu", menu);
			
			array[i] = hm;
		}
		
		return array;
	}
	
	@UserLoginToken
	@RequestMapping("findGoodInfoById")
	@ResponseBody
	public Map<String,Object> findGoodInfoById(int gid) {
		Map<String,Object> map = new HashMap();
		
		GoodInfo goodInfo = service.findGoodInfoById(gid);
		map.put("goodInfo", goodInfo);
		Menu menu = menuService.findMenuById(goodInfo.getMid());
		map.put("menu", menu);
		
		return map;
		
	}
	
	@UserLoginToken
	@RequestMapping("findGoodInfoByMenu")
	@ResponseBody
	public GoodInfo findGoodInfoByMenu(Integer mid) {
		
		return service.findGoodInfoByMenu(mid);		
	}
	
	
	@UserLoginToken
	@RequestMapping(value = "/addGoodInfo", method=RequestMethod.POST)
	@ResponseBody
	public int addGoodInfo(@RequestBody GoodInfo goodInfo) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    goodInfo.setCreateTime(date);
	    
		service.addGoodInfo(goodInfo);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeGoodInfo", method=RequestMethod.POST)
	@ResponseBody
	public int changeGoodInfo(@RequestBody GoodInfo goodInfo) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    goodInfo.setUpdateTime(date);
	    
		service.changeGoodInfo(goodInfo);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteGoodInfoById")
	@ResponseBody
	public int deleteGoodInfoById(int gid) {
		int flag = 1;
		service.deleteGoodInfoById(gid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteGoodInfoByMenu")
	@ResponseBody
	public int deleteGoodInfoByMenu(int mid) {
		int flag = 1;
		service.deleteGoodInfoByMenu(mid);
		
		return flag;
	}
}
