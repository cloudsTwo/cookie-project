package com.chinasoft.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.chinasoft.pojo.Comment;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.User;
import com.chinasoft.service.CommentService;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.UserService;

@Controller
@RequestMapping("comment")
@CrossOrigin
public class CommentController {

	@Autowired
	CommentService service;
	@Autowired
	UserService userService;
	@Autowired
	MenuService menuService;
	
	@RequestMapping("findAllComments")
	@ResponseBody
	public List<Object> findAllComments() {
		List<Object> comments = new ArrayList();
		List<Comment> allComments = service.findAllComments();
		
		for(int i = 0; i < allComments.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("comment", allComments.get(i));
			User user = userService.findOneUserById(allComments.get(i).getUid());
			map.put("user", user);
			Menu menu = menuService.findMenuById(allComments.get(i).getMid());
			map.put("menu", menu);
			comments.add(map);
		}
		
		return comments;	
	}
	
	@RequestMapping("findComment")
	@ResponseBody
	public List<Comment> findComment(int uid,int mid) {
		
		return service.findComment(uid, mid);
	}
	
	@RequestMapping("findMenuComment")
	@ResponseBody
	public Object[] findMenuComment(Integer mid) {
		List<Comment> comments = service.findMenuComment(mid);
		int length = comments.size();
		Object[] array = new Object[length];
		
		for(int i = 0; i < comments.size(); i ++) {
			Map<String,Object> hm = new HashMap<>();
			hm.put("comment", comments.get(i));
			hm.put("user", userService.findOneUserById(comments.get(i).getUid()));
			array[i] = hm;
		}
		
		return array;
	}
	
	@RequestMapping("findUserComment")
	@ResponseBody
	public List<Comment> findUserComment(int uid) {
		
		return service.findUserComment(uid);
	}
	
	@UserLoginToken
	@RequestMapping(value = "/addComment", method=RequestMethod.POST)
	@ResponseBody
	public int addComment(@RequestBody Comment comment) {
		System.out.println(comment.getCcontent());
		int flag = 1;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    comment.setCtime(date);
	    
		service.addComment(comment);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value = "/changeComment", method=RequestMethod.POST)
	@ResponseBody
	public int changeComment(@RequestBody Comment comment) {
		int flag = 1;
		service.changeComment(comment);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteComment")
	@ResponseBody
	public int deleteComment(int uid,int mid) {
		int flag = 1;
		service.deleteComment(uid, mid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteCommentById")
	@ResponseBody
	public int deleteCommentById(int cid) {
		int flag = 1;
		service.deleteCommentById(cid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteComments")
	@ResponseBody
	public int deleteComments(int mid) {
		int flag = 1;
		service.deleteComments(mid);
		
		return flag;
	}
}
