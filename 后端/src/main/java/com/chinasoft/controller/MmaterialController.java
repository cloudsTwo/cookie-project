package com.chinasoft.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.Mmaterial;
import com.chinasoft.service.MmaterialService;
@Controller
@RequestMapping("mmaterial")
@CrossOrigin
public class MmaterialController {

	@Autowired
	MmaterialService service;
	
	
	@RequestMapping("findMmaterial")
	@ResponseBody
	public List<Mmaterial> findMmaterial(int mid) {
		
		return service.findMmaterial(mid);
	}
	
	
	@UserLoginToken
	@RequestMapping(value="addMmaterial",method=RequestMethod.POST)
	@ResponseBody
	public int addMmaterial(@RequestBody Mmaterial mmaterial) {
		int flag = 1;
		service.addMmaterial(mmaterial);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteMmaterials")
	@ResponseBody
	public int deleteMmaterials(int mid) {
		int flag = 1;
		service.deleteMmaterials(mid);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping(value="/changeMmaterial",method=RequestMethod.POST)
	@ResponseBody
	public int changeMmaterial(@RequestBody Mmaterial mmaterial) {
		int flag = 1;
		
		service.changeMmaterial(mmaterial);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("deleteMmaterialById")
	@ResponseBody
	public int deleteMmaterialById(int mtId) {
		int flag = 1;
		
		service.deleteMmaterialById(mtId);
		
		return flag;
	}
	
	@UserLoginToken
	@RequestMapping("findMmaterialById")
	@ResponseBody
	public Mmaterial findMmaterialById(int mtId) {
		
		return service.findMmaterialById(mtId);
		
	}
	
}
