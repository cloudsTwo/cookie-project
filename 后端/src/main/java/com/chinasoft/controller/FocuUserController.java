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
import com.chinasoft.pojo.FocuUser;
import com.chinasoft.pojo.User;
import com.chinasoft.service.FocuUserService;
import com.chinasoft.service.UserService;

@Controller
@RequestMapping("focuUser")
@CrossOrigin
public class FocuUserController {

	@Autowired
	FocuUserService service;
	@Autowired
	UserService userService;
	
	@UserLoginToken
	@RequestMapping("findFocuUserById")
	@ResponseBody
	public FocuUser findFocuUserById(int id) {
		
		return service.findFocuUserById(id);
	}
	
	@UserLoginToken
	@RequestMapping("findFocuUserByFocu")
	@ResponseBody
	public List<FocuUser> findFocuUserByFocu(int uid) {
		
		return service.findFocuUserByFocu(uid);
	}
	
	@UserLoginToken
	@RequestMapping("findFocuUserByFollow")
	@ResponseBody
	public List<FocuUser> findFocuUserByFollow(int followId) {
		
		return service.findFocuUserByFollow(followId);
	}
	
	@UserLoginToken
	@RequestMapping("findFocuUser")
	@ResponseBody
	public FocuUser findFocuUser(Integer uid, int followId) {
		return service.findFocuUser(uid, followId);
	}
	
	@UserLoginToken
	@RequestMapping("getFocuCount")
	@ResponseBody
	public int findFocuCount(Integer uid) {
		return service.getFocuCount(uid);
	}
	
	@UserLoginToken
	@RequestMapping("getFollowCount")
	@ResponseBody
	public int findFollowCount(Integer followId) {
		return service.getFollowCount(followId);
	}
	
	@UserLoginToken
	@RequestMapping("changeFocu")
	@ResponseBody
	public Boolean changeFocuUser(int uid,int followId) {
		User user = userService.findOneUserById(uid);
		User fuser = userService.findOneUserById(followId);
		int focuNum = service.getFocuCount(uid);
		int followNum = service.getFollowCount(followId);
		
		// 如果已，取消
		if(service.findFocuUser(uid, followId) != null) {
			service.deleteFocuUser(uid,followId);
			user.setFocus(focuNum - 1);
			fuser.setFans(followNum - 1);
			userService.changeUser(user);
			userService.changeUser(fuser);
			
			return false;
			
		}else {
			// 未，添加
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String date = formatter.format(new Date());
		    
		    FocuUser focuUser = new FocuUser(uid,followId,date);
		    
			service.addFocuUser(focuUser);
			user.setFocus(focuNum + 1);
			fuser.setFans(followNum + 1);
			userService.changeUser(user);
			userService.changeUser(fuser);
			return true;
		}
	}
	
	@UserLoginToken
	@RequestMapping("isFocued")
	@ResponseBody
	public Boolean isFocued(int uid,int followId) {
		if( service.findFocuUser(uid, followId) != null ) {
			return true;
		}
		
		return false;
	}
	
	
	@UserLoginToken
	@RequestMapping(value = "/addFocuUser", method=RequestMethod.POST)
	@ResponseBody
	public int addFocuUser(@RequestBody FocuUser focuUser) {
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    focuUser.setTime(date);
	    
		service.addFocuUser(focuUser);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeFocuUser", method=RequestMethod.POST)
	@ResponseBody
	public int changeFocuUser(@RequestBody FocuUser focuUser) {
		int flag = 1;
	    
		service.changeFocuUser(focuUser);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteFocuUserById")
	@ResponseBody
	public int deleteFocuUserById(int id) {
		int flag = 1;
		service.deleteFocuUserById(id);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteFocuUser")
	@ResponseBody
	public int deleteFocuUser(int uid,int followId) {
		int flag = 1;
		service.deleteFocuUser(uid, followId);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteFocuUserByFocu")
	@ResponseBody
	public int deleteFocuUsertByUser(int uid) {
		int flag = 1;
		service.deleteFocuUserByFocu(uid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteFocuUserByFollow")
	@ResponseBody
	public int deleteFocuUsertByFollow(int followId) {
		int flag = 1;
		service.deleteFocuUserByFollow(followId);
		
		return flag;
	}
}
