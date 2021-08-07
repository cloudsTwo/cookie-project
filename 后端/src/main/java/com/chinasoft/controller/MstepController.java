package com.chinasoft.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import com.chinasoft.pojo.Mstep;
import com.chinasoft.service.MstepService;
@Controller
@RequestMapping("mstep")
@CrossOrigin
public class MstepController {

	@Autowired
	MstepService service;
	
	
	@RequestMapping("findSteps")
	@ResponseBody
	public List<Mstep> findSteps(int mid) {
		
		return service.findMenuSteps(mid);
	}
	
	
	@RequestMapping("findOneStep")
	@ResponseBody
	public Mstep findOneStep(int stepId) {
		
		return service.findStepById(stepId);
	}
	
	
	@UserLoginToken
	@RequestMapping(value="addStep",method=RequestMethod.POST)
	@ResponseBody
	public int addStep(@RequestBody Mstep mstep) {
		int flag = 1;
		service.addOneStep(mstep);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value="changeStep",method=RequestMethod.POST)
	@ResponseBody
	public int changeStep(@RequestBody Mstep mstep) {
		int flag = 1;
		service.changeOneStep(mstep);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteOneStep")
	@ResponseBody
	public int deleteOneStep(int stepId) {
		int flag = 1;
		service.deleteStepById(stepId);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteSteps")
	@ResponseBody
	public int deleteSteps(int mid) {
		int flag = 1;
		service.deleteMenuSteps(mid);
		
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
//					File file2 = new File("C:\\Users\\良学的电脑\\Desktop\\作业存档\\day07\\src\\main\\resources\\static\\files\\"+file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file2);

			IOUtils.copy(inputStream, fos);
			inputStream.close();
			fos.close();
//					service.fileUpload("D:\\java\\"+file.getOriginalFilename());	
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
				"D:\\vue_workspace\\cookie-project\\src\\assets\\img\\menu\\" + filename);// 这个路径为磁盘开始

		byte[] bytes = FileUtils.readFileToByteArray(file);
		sos.write(bytes);
		sos.flush();
		sos.close();
	}
}
