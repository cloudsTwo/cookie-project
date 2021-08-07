package com.chinasoft.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.FocuUser;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.Order;
import com.chinasoft.pojo.OrderGoods;
import com.chinasoft.pojo.User;
import com.chinasoft.service.CollectService;
import com.chinasoft.service.CommentService;
import com.chinasoft.service.FocuUserService;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.OrderGoodsService;
import com.chinasoft.service.OrderService;
import com.chinasoft.service.ShopcartService;
import com.chinasoft.service.TokenService;
import com.chinasoft.service.UserService;

@Controller
@RequestMapping("user")
@CrossOrigin
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	MenuService menuService;
	@Autowired
	CommentService commentService;
	@Autowired
	CollectService collectService;
	@Autowired
	FocuUserService focuUserService;
	@Autowired
	ShopcartService shopcartService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGoodsService orderGoodsService;
	
	
	@Autowired
    TokenService tokenService;

	// 登录（需要账号密码）
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> login(String name, String password, HttpServletRequest request, HttpServletResponse response) {

		Map<String,Object> hm = new HashMap<>();
		
		User user = service.LoginUser(name, password);
		hm.put("user", user);
		
		if (user == null) {
			System.out.println("该用户不存在");
			return null;
		} else {
			System.out.println("登录成功");
		}
		
		// 生成token并返回
		String token = tokenService.getToken(user);
		hm.put("token", token);
		
		return hm;
	}

	/*
	 * @RequestMapping("login")
	 * 
	 * @ResponseBody public JSONObject login(String name, String password,
	 * HttpSession session, HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * System.out.println("login"); JSONObject res = service.doLogin(name, password,
	 * session, request, response);
	 * 
	 * return res; }
	 * 
	 *//**
		 * 退出登录
		 *//*
			 * @RequestMapping(value = "logout") public String logout(HttpSession session,
			 * HttpServletRequest request, HttpServletResponse response) { //
			 * 删除session里面的用户信息 session.removeAttribute("session_user"); // 保存cookie，实现自动登录
			 * Cookie cookie_username = new Cookie("cookie_username", ""); Cookie
			 * cookie_userid = new Cookie("cookie_userid", "");
			 * 
			 * // 设置cookie的持久化时间，0 cookie_username.setMaxAge(0); cookie_userid.setMaxAge(0);
			 * 
			 * // 设置为当前项目下都携带这个cookie cookie_username.setPath(request.getContextPath());
			 * cookie_userid.setPath(request.getContextPath());
			 * 
			 * // 向客户端发送cookie response.addCookie(cookie_username);
			 * response.addCookie(cookie_userid);
			 * 
			 * return "logout"; }
			 */

	// 注册账号（需要一个新用户对象）
	@RequestMapping(value = "/registered", method = RequestMethod.POST)
	@ResponseBody
	public int registered(@RequestBody User user) {
		int flag = 1;
		
		service.registered(user);

		return flag;
	}

	// 修改密码
	@UserLoginToken
	@RequestMapping("changePassword")
	@ResponseBody
	public int changePassword(int uid, String password) {
		int flag = 1;
		service.changePassword(uid,password);

		return flag;
	}
	
	// 修改头像
	@UserLoginToken
	@RequestMapping("changeAvatar")
	@ResponseBody
	public int changeAvatar(int uid, String avatar) {
		int flag = 1;
		service.changeAvatar(uid,avatar);

		return flag;
	}
	
	// 修改地址
	@UserLoginToken
	@RequestMapping(value = "/changeAddress", method = RequestMethod.POST)
	@ResponseBody
	public int changeAddress(int uid, String address) {
		int flag = 1;
		
		service.changeAddress(uid, address);
		return flag;
	}
	
	
	// 删除用户
	@UserLoginToken
	@RequestMapping("deleteUser")
	@ResponseBody
	public int deleteUser(int uid) {
		int flag = 1;
		service.deleteUser(uid);
		List<Menu> menus = menuService.findMenuByUser(uid);
		
		// 删除发布的食谱
		for(int i = 0; i < menus.size(); i++) {
			menuService.deleteMenu(menus.get(i).getMid());
		}
		
		// 删除发布的评论
		commentService.deleteCommentByUser(uid);
		
		// 删除所有收藏
		collectService.deleteCollectByUser(uid);
		
		// 删除购物车
		shopcartService.deleteShopcartByUser(uid);
		
		// 删除关注信息
		List<FocuUser> follows = focuUserService.findFocuUserByFocu(uid);
		List<FocuUser> focus = focuUserService.findFocuUserByFollow(uid);
		
		// 所有粉丝关注数减1
		for(int i = 0; i < follows.size(); i++) {
			User user = service.findOneUserById(follows.get(i).getFollowId());
			user.setFocus(user.getFocus() - 1);
			service.changeUser(user);
		}
		
		// 所有关注者粉丝数减1
		for(int i = 0; i < focus.size(); i++) {
			User user = service.findOneUserById(focus.get(i).getUid());
			user.setFans(user.getFans() - 1);
			service.changeUser(user);
		}
		
		focuUserService.deleteFocuUserByFocu(uid);
		focuUserService.deleteFocuUserByFollow(uid);
		
		// 删除订单和订单信息
		List<Order> orders = orderService.findOrderByUser(uid);
		
		for(int i = 0; i < orders.size(); i++) {
			Order o = orders.get(i);
			o.setStatus(-1);
			orderService.changeOrder(o);
			
			OrderGoods og = orderGoodsService.findOrderGoodsByOrder(o.getOid());
			og.setStatus(-1);
			orderGoodsService.changeOrderGoods(og);
		}
		
		return flag;
	}

	// 修改用户信息
	@UserLoginToken
	@RequestMapping(value = "/changeUser", method = RequestMethod.POST)
	@ResponseBody
	public int changeUser(@RequestBody User user) {
		System.out.println(user.getName()+":" + user.getUid());
		int flag = 1;

		service.changeUser(user);
		
		return flag;
	}

	// 查找一用户（以id）
	@UserLoginToken
	@RequestMapping("findOneUserById")
	@ResponseBody
	public User findOneUserById(int uid) {

		User user = service.findOneUserById(uid);

		return user;

	}

	// 查找一用户（以电话号码）
	@RequestMapping("findOneUserByNumber")
	@ResponseBody
	public User findOneUserByNumber(String phoneNumber) {

		User user = service.findOneUserByNumber(phoneNumber);

		return user;

	}

	// 查找一用户（以姓名）
	@RequestMapping("findOneUserByName")
	@ResponseBody
	public User findOneUserByName(String name) {

		User user = service.findOneUserByName(name);

		return user;

	}

	// 上传文件
	@UserLoginToken
	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(MultipartFile file, Model model) {
		try {
			System.out.println("这个是上传的方法");
			String filePath = "D:\\vue_workspace\\cookie-project1\\src\\assets\\img\\user\\"
					+ file.getOriginalFilename();
			
			// 获取到文件的输入流
			InputStream inputStream = file.getInputStream();
			File file2 = new File(filePath);
//			File file2 = new File("C:\\Users\\良学的电脑\\Desktop\\作业存档\\day07\\src\\main\\resources\\static\\files\\"+file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file2);

			IOUtils.copy(inputStream, fos);
			inputStream.close();
			fos.close();
//			service.fileUpload("D:\\java\\"+file.getOriginalFilename());	
			return filePath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 下载文件（需要文件名）
	@RequestMapping("download")
	@ResponseBody
	public void download(String filename, HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 设置响应流文件进行下载
		resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
		ServletOutputStream sos = resp.getOutputStream();
		/*
		 * System.out.println(req.getServletContext().getRealPath("//static//files"));
		 */
		File file = new File(
				"C:\\Users\\良学的电脑\\Desktop\\作业存档\\day07\\src\\main\\resources\\static\\files\\" + filename);// 这个路径为磁盘开始

		byte[] bytes = FileUtils.readFileToByteArray(file);
		sos.write(bytes);
		sos.flush();
		sos.close();
	}
//
//	// 查找所有文章
//	@RequestMapping("findAllArticle")
//	@ResponseBody
//	public List<Article> findAllArticle() {
//		System.out.println(service.findAllArticle());
//		List<Article> article = service.findAllArticle();
//		return article;
//
//	}
//
//	// 查找一篇文章（以文章id）
//	@RequestMapping("findOneArticleById")
//	@ResponseBody
//	public Article findOneArticleById(int article_id) {
//
//		Article article = service.findOneArticleById(article_id);
//		System.out.println(article);
//		setPostPageviews(article);
//		return article;
//
//	}
//
//	// 浏览人数+1
//	@RequestMapping("setPostPageviews")
//	@ResponseBody
//	public void setPostPageviews(Article article) {
//		article.setPostPageviews(article.getPostPageviews() + 1);
//		service.setPostPageviews(article);
//	}
//
//	// 增加一篇文章
//	@RequestMapping("addArticle")
//	@ResponseBody
//	public int addArticle(Article article) {
//		int flag = 1;
//		article.setPostPageviews(0);
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = formatter.format(new Date());
//		article.setPostTime(date);
//		service.addArticle(article);
//		int article_id = article.getArticle_id();
//
//		System.out.println(date);
//		System.out.println(article_id);
//		return flag;
//	}
//
//	// 删除一篇文章
//	@RequestMapping("deleteArticle")
//	@ResponseBody
//	public int deleteArticle(int article_id) {
//		int flag = 1;
//		service.deleteArticle(article_id);
//		return flag;
//	}
//
//	// 设置最后评论时间
//	@RequestMapping("setLastCm")
//	@ResponseBody
//	public void setLastCm(String date, int article_Id) {
//		service.setLastCm(date, article_Id);
//	}
//
//	// 增加一个评论
//	@RequestMapping("addCm")
//	@ResponseBody
//	public int addCm(Cm cm) {
//		int flag = 1;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = formatter.format(new Date());
//		cm.setCmTime(date);
//		setLastCm(date, cm.getArticle_Id());
//		service.addCm(cm);
//		return flag;
//	}



	// 查看所有用户
	@RequestMapping("findAllUser")
	@ResponseBody
	public List<User> findAllUser() {
		List<User> user = service.findAllUser();
		return user;

	}

	// 上传照片
	@RequestMapping("UploadPhoto")
	@ResponseBody
	public int UploadPhoto(User user) {
		int flag = 1;
		service.UploadPhoto(user);
		return flag;
	}

}
