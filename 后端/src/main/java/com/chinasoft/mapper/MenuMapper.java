package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Menu;

public interface MenuMapper {
	
	List<Menu> findAllMenu();
	
	void changeMenu(Menu menu);
	
	void checkMenu(int mid);
	
	List<Menu> findCheckedMenu(int isChecked);
	
	List<Menu> findCheckedMenuByName(@Param("isChecked")int isChecked, @Param("mname")String mname);
	
	void changeMenuView(@Param("views")int views,@Param("mid")int mid);
	
	void changeMenuCollect(@Param("collectCount")int collectCount,@Param("mid")int mid);
	
	void changeMenuLike(@Param("likeCount")int likeCount,@Param("mid")int mid);
	
	void addMenu(Menu menu);
	
	Menu findMenuById(int mid);
	
	Menu findLastMenu();
	
	List<Menu> findMenuByName(String mname);
	
	List<Menu> findMenuByUser(int uid);
	
	void deleteMenu(int mid);
	
	void deleteMenuByUser(int uid);

}
