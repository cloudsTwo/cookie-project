package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Mmaterial;

public interface MmaterialService {
	List<Mmaterial> findMmaterial(int mid);
	
	void addMmaterial(Mmaterial mmaterial);
	
	void deleteMmaterials(int mid);
	
	void changeMmaterial(Mmaterial mmaterial);
	
	void deleteMmaterialById(int mtId);
	
	Mmaterial findMmaterialById(int mtId);
}
