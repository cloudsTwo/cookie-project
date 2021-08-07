package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.CommentMapper;
import com.chinasoft.pojo.Comment;
import com.chinasoft.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentMapper mapper;

	@Override
	public List<Comment> findComment(int uid, int mid) {
		// TODO Auto-generated method stub
		return mapper.findComment(uid, mid);
	}

	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		mapper.addComment(comment);
	}

	@Override
	public void changeComment(Comment comment) {
		// TODO Auto-generated method stub
		mapper.changeComment(comment);
	}

	@Override
	public void deleteComment(int uid, int mid) {
		// TODO Auto-generated method stub
		mapper.deleteComment(uid, mid);
	}

	@Override
	public List<Comment> findMenuComment(int mid) {
		// TODO Auto-generated method stub
		return mapper.findMenuComment(mid);
	}

	@Override
	public List<Comment> findUserComment(int uid) {
		// TODO Auto-generated method stub
		return mapper.findUserComment(uid);
	}

	@Override
	public void deleteCommentById(int cid) {
		// TODO Auto-generated method stub
		mapper.deleteCommentById(cid);
	}

	@Override
	public void deleteComments(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteComments(mid);
	}

	@Override
	public List<Comment> findAllComments() {
		// TODO Auto-generated method stub
		return mapper.findAllComments();
	}

	@Override
	public void deleteCommentByUser(int uid) {
		// TODO Auto-generated method stub
		mapper.deleteCommentByUser(uid);
	}

}
