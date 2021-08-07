package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.CollectMapper;
import com.chinasoft.mapper.CommentMapper;
import com.chinasoft.mapper.MenuMapper;
import com.chinasoft.mapper.MmaterialMapper;
import com.chinasoft.mapper.MstepMapper;
import com.chinasoft.mapper.ShopcartMapper;
import com.chinasoft.pojo.Menu;
import com.chinasoft.pojo.Shopcart;
import com.chinasoft.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuMapper mapper;
	@Autowired
	MstepMapper mstepMapper;
	@Autowired
	MmaterialMapper mmaterialMapper;
	@Autowired
	CommentMapper commentMapper;
	@Autowired
	CollectMapper collectMapper;
	@Autowired
	ShopcartMapper shopcartMapper;

	@Override
	public List<Menu> findAllMenu() {
		// TODO Auto-generated method stub
		return mapper.findAllMenu();
	}

	@Override
	public void changeMenu(Menu menu) {
		// TODO Auto-generated method stub
		mapper.changeMenu(menu);
	}

	@Override
	public void addMenu(Menu menu) {
		// TODO Auto-generated method stub
		mapper.addMenu(menu);
	}

	@Override
	public Menu findMenuById(int mid) {
		// TODO Auto-generated method stub
		return mapper.findMenuById(mid);
	}
	
	@Override
	public Menu findLastMenu() {
		// TODO Auto-generated method stub
		return mapper.findLastMenu();
	}

	@Override
	public List<Menu> findMenuByName(String mname) {
		// TODO Auto-generated method stub
		return mapper.findMenuByName(mname);
	}

	@Override
	public void deleteMenu(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteMenu(mid);
		mmaterialMapper.deleteMmaterials(mid);
		mstepMapper.deleteMenuSteps(mid);
		commentMapper.deleteComments(mid);
		collectMapper.deleteCollects(mid);
		
		List<Shopcart> shopcarts = shopcartMapper.findShopcartByMenu(mid);
		for(int i = 0; i < shopcarts.size(); i++) {
			Shopcart s = shopcarts.get(i);
			s.setStatus(-1);
			shopcartMapper.changeShopcart(s);
		}
	}

	@Override
	public void changeMenuView(int views, int mid) {
		// TODO Auto-generated method stub
		mapper.changeMenuView(views, mid);
		
	}

	@Override
	public void changeMenuCollect(int collectCount, int mid) {
		// TODO Auto-generated method stub
		mapper.changeMenuCollect(collectCount, mid);
	}

	@Override
	public void changeMenuLike(int likeCount, int mid) {
		System.out.println(likeCount+":"+mid);
		// TODO Auto-generated method stub
		mapper.changeMenuLike(likeCount, mid);
	}

	@Override
	public List<Menu> findMenuByUser(int uid) {
		// TODO Auto-generated method stub
		return mapper.findMenuByUser(uid);
	}

	@Override
	public void deleteMenuByUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteMenuByUser(uid);
	}

	@Override
	public void checkMenu(int mid) {
		// TODO Auto-generated method stub
		mapper.checkMenu(mid);
	}

	@Override
	public List<Menu> findCheckedMenu(int isChecked) {
		// TODO Auto-generated method stub
		return mapper.findCheckedMenu(isChecked);
	}

	@Override
	public List<Menu> findCheckedMenuByName(int isChecked, String mname) {
		// TODO Auto-generated method stub
		return mapper.findCheckedMenuByName(isChecked, mname);
	}


}
