package com.example.hospital.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.hospital.dto.Hospital;

@Mapper
public interface HospitalDao {
	public int maxNum() throws Exception;
	
	public void insertData(Hospital hospital) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<Hospital> getLists(String searchKey, String searchValue, int start, int end) throws Exception;
	
	public Hospital getReadData(int num) throws Exception;
}
