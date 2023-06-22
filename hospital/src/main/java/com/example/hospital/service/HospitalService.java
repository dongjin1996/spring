package com.example.hospital.service;

import java.util.List;

import com.example.hospital.dto.Hospital;

public interface HospitalService {
	public int maxNum() throws Exception;
	
	public void insertData(Hospital hospital) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<Hospital> getLists(String searchKey, String searchValue, int start, int end) throws Exception;
	
	public Hospital getReadData(int num) throws Exception;
	
	public void updateData(Hospital hospital) throws Exception;
	
	public int deleteData(int num) throws Exception;

}
