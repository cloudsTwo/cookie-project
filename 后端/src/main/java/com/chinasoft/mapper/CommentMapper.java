package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Comment;

public interface CommentMapper {
	List<Comment> findAllComments();
			
	List<Comment> findComment(@Param("uid")int uid, @Param("mid")int mid);
	
	List<Comment> findMenuComment(int mid);
	
	List<Comment> findUserComment(int uid);
	
	void addComment(Comment comment);
	
	void changeComment(Comment comment);
	
	void deleteCommentById(int cid);
	
	void deleteComments(int mid);
	
	void deleteCommentByUser(int uid);
	
	void deleteComment(@Param("uid")int uid, @Param("mid")int mid);

}
