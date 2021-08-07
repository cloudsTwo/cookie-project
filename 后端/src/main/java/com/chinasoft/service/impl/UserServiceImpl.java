package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.UserMapper;
import com.chinasoft.pojo.User;
import com.chinasoft.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper mapper;

	@Override
	public User LoginUser(String name, String password) {
		// TODO Auto-generated method stub
		User user = mapper.login(name, password);

		return user;
	}

	/**
	 * 执行登录
	 */
	/*
	 * @Override public JSONObject doLogin(String name, String password, HttpSession
	 * session, HttpServletRequest request, HttpServletResponse response) { //
	 * 最终返回的对象 JSONObject res = new JSONObject(); res.put("code", 0); if
	 * (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) { res.put("msg",
	 * "请输入手机号或密码"); return res; }
	 * 
	 * User dbUser = mapper.findOneUserByName(name); if (null == dbUser) {
	 * res.put("msg", "该账号不存在，请检查后重试"); return res; }
	 * 
	 * // 验证密码是否正确 String newPassword = password; if
	 * (!newPassword.equals(dbUser.getPassword())) { res.put("msg",
	 * "手机号或密码错误，请检查后重试"); return res; }
	 * 
	 * // 判断账户状态 // if (1 != dbUser.getStatus()) { // res.put("msg",
	 * "该账号已被冻结，请联系管理员"); // return res; // }
	 * 
	 * // 将登录用户信息保存到session中 session.setAttribute("session_user", dbUser);
	 * 
	 * // 保存cookie，实现自动登录 Cookie cookie_username = new Cookie("cookie_username",
	 * name); Cookie cookie_userid = new Cookie("cookie_userid", "" +
	 * dbUser.getUid());
	 * 
	 * // 设置cookie的持久化时间，30天 cookie_username.setMaxAge(30 * 24 * 60 * 60);
	 * cookie_userid.setMaxAge(30 * 24 * 60 * 60);
	 * 
	 * // 设置为当前项目下都携带这个cookie cookie_username.setPath(request.getContextPath());
	 * cookie_userid.setPath(request.getContextPath());
	 * 
	 * // 向客户端发送cookie response.addCookie(cookie_username);
	 * response.addCookie(cookie_userid);
	 * 
	 * res.put("code", 1); res.put("msg", "登录成功"); res.put("user",dbUser);
	 * 
	 * return res; }
	 */

	@Override
	public void registered(User user) {

		mapper.registered(user);
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return mapper.findAllUser();
	}

	@Override
	public void changeUser(User user) {
		mapper.changeUser(user);

	}

	@Override
	public User findOneUserById(int uid) {
		// TODO Auto-generated method stub
		return mapper.findOneUserById(uid);
	}

	@Override
	public User findOneUserByNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return mapper.findOneUserByNumber(phoneNumber);
	}

	@Override
	public User findOneUserByName(String name) {
		// TODO Auto-generated method stub
		return mapper.findOneUserByName(name);
	}


	@Override
	public void UploadPhoto(User user) {
		mapper.UploadPhoto(user);

	}

	@Override
	public void changePassword(int uid, String password) {
		// TODO Auto-generated method stub
		mapper.changePassword(uid, password);
	}

	@Override
	public void changeAddress(int uid, String address) {
		// TODO Auto-generated method stub
		mapper.changeAddress(uid, address);
	}

	@Override
	public void deleteUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteUser(uid);
		
	}

	@Override
	public void changeAvatar(int uid, String avatar) {
		// TODO Auto-generated method stub
		mapper.changeAvatar(uid, avatar);
	}

}
