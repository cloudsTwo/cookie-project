package com.chinasoft.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.Like;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.User;
import com.chinasoft.service.LikeService;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.UserService;


@Controller
@RequestMapping("like")
@CrossOrigin
public class LikeController {

	@Autowired
	LikeService service;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	UserService userService;
	
	@UserLoginToken
	@RequestMapping("changeLike")
	@ResponseBody
	public Boolean changeLike(int uid,int mid) {
		Menu menu = menuService.findMenuById(mid);
		User user = userService.findOneUserById(menu.getUid());
		int likeNum = menu.getLikeCount();
		
		// 如果已点赞，取消点赞
		if(service.findLike(uid, mid) != null) {
			likeNum -= 1;
			service.deleteLike(uid, mid);
			menuService.changeMenuLike(likeNum, mid);
			
			user.setGetLike(user.getGetLike() - 1);
			userService.changeUser(user);
			
			return false;
			
		}else {
			// 未收藏，添加收藏
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String date = formatter.format(new Date());
		    
			Like like = new Like(uid,mid,date);
		    
			likeNum += 1;
			service.addLike(like);
			menuService.changeMenuLike(likeNum, mid);
			
			user.setGetLike(user.getGetLike() + 1);
			userService.changeUser(user);
			
			return true;
		}
	}
	
	@UserLoginToken
	@RequestMapping("isLike")
	@ResponseBody
	public Boolean isLike(int uid,int mid) {
		if( service.findLike(uid, mid) != null ) {
			return true;
		}
		
		return false;
	}
	
	
	@RequestMapping("findLike")
	@ResponseBody
	public Like findLike(int uid,int mid) {
		
		return service.findLike(uid, mid);
	}
	
	
	@RequestMapping("addLike")
	@ResponseBody
	public int addLike(Like like) {
		int flag = 1;
		service.addLike(like);
		
		return flag;
	}
	
	@RequestMapping("deleteLike")
	@ResponseBody
	public int deleteLike(int uid,int mid) {
		int flag = 1;
		service.deleteLike(uid, mid);
		
		return flag;
	}
	
	
}
