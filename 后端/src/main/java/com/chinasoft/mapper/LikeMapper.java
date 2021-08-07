package com.chinasoft.mapper;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Like;

public interface LikeMapper {
	Like findLike(@Param("uid")int uid, @Param("mid")int mid);
	
	void addLike(Like like);
	
	void deleteLike(@Param("uid")int uid, @Param("mid")int mid);
	
}
