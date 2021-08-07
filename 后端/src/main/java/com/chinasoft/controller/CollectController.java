package com.chinasoft.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.Collect;
import com.chinasoft.pojo.Menu;
import com.chinasoft.service.CollectService;
import com.chinasoft.service.MenuService;


@Controller
@RequestMapping("collect")
@CrossOrigin
public class CollectController {

	@Autowired
	CollectService service;
	
	@Autowired
	MenuService menuService;
	
	@UserLoginToken
	@RequestMapping("changeCollect")
	@ResponseBody
	public Boolean changeCollect(int uid,int mid) {
		Menu menu = menuService.findMenuById(mid);
		int collectNum = menu.getCollectCount();
		
		// 如果已收藏，取消收藏
		if(service.findCollect(uid, mid) != null) {
			service.deleteCollect(uid, mid);
			menuService.changeMenuCollect(collectNum-1, mid);
			
			return false;
			
		}else {
			// 未收藏，添加收藏
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String date = formatter.format(new Date());
		    
			Collect collect = new Collect(mid,uid,date);
		    
			service.addCollect(collect);
			menuService.changeMenuCollect(collectNum+1, mid);
			return true;
		}
	}
	
	@UserLoginToken
	@RequestMapping("isCollect")
	@ResponseBody
	public Boolean isCollect(int uid,int mid) {
		if( service.findCollect(uid, mid) != null ) {
			return true;
		}
		
		return false;
	}
	
	@UserLoginToken
	@RequestMapping("findCollect")
	@ResponseBody
	public Collect findCollect(int uid,int mid) {
		return service.findCollect(uid, mid);
	}
	
	@UserLoginToken
	@RequestMapping("findCollectByUser")
	@ResponseBody
	public Menu[] findCollectByUser(Integer uid) {
		
		List<Collect> collects = service.findCollectByUser(uid);
		Menu[] menus = new Menu[collects.size()];
		
		for(int i = 0; i < collects.size(); i++) {
			menus[i] = menuService.findMenuById(collects.get(i).getMid());
		}
		
		return menus;
	}
	
	@UserLoginToken
	@RequestMapping(value="addCollect",method=RequestMethod.POST)
	@ResponseBody
	public int addCollect(@RequestBody Collect collect) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    collect.setTime(date);
	    
		service.addCollect(collect);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteCollect")
	@ResponseBody
	public int deleteCollect(int uid,int mid) {
		int flag = 1;
		service.deleteCollect(uid, mid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteCollects")
	@ResponseBody
	public int deleteCollects(int mid) {
		int flag = 1;
		service.deleteCollects(mid);
		
		return flag;
	}
	
}
