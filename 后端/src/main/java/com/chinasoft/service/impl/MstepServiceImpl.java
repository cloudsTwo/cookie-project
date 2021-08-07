package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.MstepMapper;
import com.chinasoft.pojo.Mstep;
import com.chinasoft.service.MstepService;

@Service
public class MstepServiceImpl implements MstepService{
	
	@Autowired
	MstepMapper mapper;

	@Override
	public List<Mstep> findMenuSteps(int mid) {
		// TODO Auto-generated method stub
		return mapper.findMenuSteps(mid);
	}

	@Override
	public void changeOneStep(Mstep mstep) {
		// TODO Auto-generated method stub
		mapper.changeOneStep(mstep);
	}

	@Override
	public Mstep findStepById(int stepId) {
		// TODO Auto-generated method stub
		return mapper.findStepById(stepId);
	}

	@Override
	public void addOneStep(Mstep mstep) {
		// TODO Auto-generated method stub
		mapper.addOneStep(mstep);
	}

	@Override
	public void deleteStepById(int stepId) {
		// TODO Auto-generated method stub
		mapper.deleteStepById(stepId);
	}

	@Override
	public void deleteMenuSteps(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteMenuSteps(mid);
	}

}
