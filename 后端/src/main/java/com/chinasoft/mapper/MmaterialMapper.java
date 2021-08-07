package com.chinasoft.mapper;

import java.util.List;

import com.chinasoft.pojo.Mmaterial;

public interface MmaterialMapper {
	List<Mmaterial> findMmaterial(int mid);
	
	void addMmaterial(Mmaterial mmaterial);
	
	void deleteMmaterials(int mid);
	
	void changeMmaterial(Mmaterial mmaterial);
	
	void deleteMmaterialById(int mtId);
	
	Mmaterial findMmaterialById(int mtId);
}
