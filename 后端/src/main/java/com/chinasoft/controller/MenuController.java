package com.chinasoft.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chinasoft.pojo.GoodInfo;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.Mmaterial;
import com.chinasoft.pojo.Mstep;
import com.chinasoft.pojo.User;
import com.chinasoft.service.GoodInfoService;
import com.chinasoft.service.MenuService;
import com.chinasoft.service.MmaterialService;
import com.chinasoft.service.MstepService;
import com.chinasoft.service.UserService;

@Controller
@RequestMapping("menu")
@CrossOrigin
public class MenuController {

	@Autowired
	MenuService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MmaterialService materialService;
	
	@Autowired
	MstepService stepService;
	
	@Autowired
	GoodInfoService goodInfoService;
	
	// 查找全部食谱
	@RequestMapping("findAllMenu")
	@ResponseBody
	public List<Menu> findAllMenu() {
		
		return service.findAllMenu();
	}
	
	// 通过用户查找食谱
	@UserLoginToken
	@RequestMapping("findMenuByUser")
	@ResponseBody
	public List<Menu> findMenuByUser(int uid) {
		
		return service.findMenuByUser(uid);
	}
	
	// 查找最新食谱
	@RequestMapping("findLastMenu")
	@ResponseBody
	public Menu findLastMenu() {
		
		return service.findLastMenu();
	}
	
	//通过id查找食谱
	@RequestMapping("findMenuById")
	@ResponseBody
	public Map<String,Object> findMenuById(int mid) {
		Map<String, Object> map = new HashMap<>();
		
		Menu menu = service.findMenuById(mid);
		service.changeMenuView(menu.getViews() + 1, mid); 	// 改变浏览量
		
		map.put("menu", menu);	// 获取数据
		User user = userService.findOneUserById(menu.getUid());
		map.put("user", user);
		List<Mmaterial> material = materialService.findMmaterial(mid);
		map.put("materials", material);
		List<Mstep> steps = stepService.findMenuSteps(mid);
		map.put("steps", steps);
		GoodInfo goodInfo =  goodInfoService.findGoodInfoByMenu(mid);
		if(goodInfo != null) {
			map.put("goodInfo",goodInfo);
		}
		
		return map;
		
	}
	
	
	// 通过菜谱名查找食谱
	@RequestMapping("findMenuByName")
	@ResponseBody
	public List<Menu> findMenuByName(String mname) {
		
		List<Menu> menus = service.findMenuByName(mname);
		
		return menus;
		
	}
	
	@UserLoginToken
	@RequestMapping(value="/addMenu",method=RequestMethod.POST)
	@ResponseBody
	public Menu addMenu(@RequestBody Menu menu) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = formatter.format(new Date());
		
	    menu.setReportTime(date);
		
		service.addMenu(menu);
		
		// 返回添加的menu;
		Menu newMenu = service.findLastMenu();
		
		return newMenu;
		
	}
		
	@UserLoginToken
	@RequestMapping(value="/changeMenu",method=RequestMethod.POST)
	@ResponseBody
	public int changeMenu(@RequestBody Menu menu) {
		int flag = 1;
		
		service.changeMenu(menu);
		
		return flag;
	}
	
	@RequestMapping("changeMenuView")
	@ResponseBody
	public int changeMenuView(int views,int mid) {
		int flag = 1;
		
		service.changeMenuView(views,mid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("changeMenuCollect")
	@ResponseBody
	public int changeMenuCollect(int collectCount,int mid) {
		int flag = 1;
		
		service.changeMenuCollect(collectCount,mid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("changeMenuLike")
	@ResponseBody
	public int changeMenuLike(int likeCount,int mid) {
		int flag = 1;
		
		service.changeMenuLike(likeCount,mid);
		
		return flag;
	}
	
	
	@UserLoginToken
	@RequestMapping("deleteMenu")
	@ResponseBody
	public int deleteMenu(int mid) {
		int flag = 1;
		
		service.deleteMenu(mid);
		
		return flag;
		
	}
	
	
	
	// 上传文件
	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(MultipartFile file, Model model) {
		try {
			System.out.println("这个是上传的方法");
			String filePath = "D:\\vue_workspace\\cookie-project1\\src\\assets\\img\\menu\\"
					+ file.getOriginalFilename();
			// 获取到文件的输入流

			InputStream inputStream = file.getInputStream();
			File file2 = new File(filePath);
			
			// 目录是否存在
			if (!file2.exists() && !file2.isDirectory()) {
		    	System.out.println("目录不存在，创建目录:" + file2);
		    	file2.mkdirs(); // mkdir()不会创建目录,找不到相应路径时返回false;而mkdirs()当目录不存在时则会创建相应目录
			}
			
//			File file2 = new File("C:\\Users\\良学的电脑\\Desktop\\作业存档\\day07\\src\\main\\resources\\static\\files\\"+file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file2);

			IOUtils.copy(inputStream, fos);
			inputStream.close();
			fos.close();
//				service.fileUpload("D:\\java\\"+file.getOriginalFilename());	
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
				"D:\\vue_workspace\\cookie-project1\\src\\assets\\img\\menu\\" + filename);// 这个路径为磁盘开始

		byte[] bytes = FileUtils.readFileToByteArray(file);
		sos.write(bytes);
		sos.flush();
		sos.close();
	}
	

	@UserLoginToken
	@RequestMapping("findCheckedMenu")
	@ResponseBody
	public List<Menu> findCheckedMenu(int isChecked) {
		
		return service.findCheckedMenu(isChecked);
	}
	
	@UserLoginToken
	@RequestMapping("findCheckedMenuByName")
	@ResponseBody
	public List<Menu> findCheckedMenuByName(int isChecked, String mname) {
		
		return service.findCheckedMenuByName(isChecked, mname);
	}
	
	@UserLoginToken
	@RequestMapping("checkMenu")
	@ResponseBody
	public void checkMenu(int mid) {
		
		service.checkMenu(mid);
	}
}
