package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.chinasoft.pojo.Mstep;

public interface MstepMapper {
	List<Mstep> findMenuSteps(int mid);
	
	void changeOneStep(Mstep mstep);
	
	Mstep findStepById(@Param("stepId")int stepId);
	
	void addOneStep(Mstep mstep);
	
	void deleteStepById(@Param("stepId")int stepId);
	
	void deleteMenuSteps(int mid);
}
