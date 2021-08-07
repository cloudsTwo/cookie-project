package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.MmaterialMapper;
import com.chinasoft.pojo.Mmaterial;
import com.chinasoft.service.MmaterialService;

@Service
public class MmaterialServiceImpl implements MmaterialService{
	
	@Autowired
	MmaterialMapper mapper;

	@Override
	public List<Mmaterial> findMmaterial(int mid) {
		// TODO Auto-generated method stub
		return mapper.findMmaterial(mid);
	}

	@Override
	public void addMmaterial(Mmaterial mmaterial) {
		// TODO Auto-generated method stub
		mapper.addMmaterial(mmaterial);
	}

	@Override
	public void deleteMmaterials(int mid) {
		// TODO Auto-generated method stub
		mapper.deleteMmaterials(mid);
	}

	@Override
	public void changeMmaterial(Mmaterial mmaterial) {
		// TODO Auto-generated method stub
		mapper.changeMmaterial(mmaterial);
	}

	@Override
	public void deleteMmaterialById(int mtId) {
		// TODO Auto-generated method stub
		mapper.deleteMmaterialById(mtId);
	}

	@Override
	public Mmaterial findMmaterialById(int mtId) {
		// TODO Auto-generated method stub
		return mapper.findMmaterialById(mtId);
	}
	
	
}
