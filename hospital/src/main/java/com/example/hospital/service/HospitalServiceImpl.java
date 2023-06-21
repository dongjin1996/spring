package com.example.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospital.dao.HospitalDao;
import com.example.hospital.dto.Hospital;

@Service
public class HospitalServiceImpl implements HospitalService{
	
	@Autowired
	private HospitalDao hospitalMapper;
	
	@Override
	public int maxNum() throws Exception {
		return hospitalMapper.maxNum(); 
	}

	@Override
	public void insertData(Hospital hospital) throws Exception {
		hospitalMapper.insertData(hospital);
	}

	@Override
	public int getDataCount(String searchKey, String searchValue) throws Exception {
		return hospitalMapper.getDataCount(searchKey, searchValue);
	}


	@Override
	public List<Hospital> getLists(String searchKey, String searchValue, int start, int end) throws Exception {
		return hospitalMapper.getLists(searchKey, searchValue, start, end);
	}

	@Override
	public Hospital getReadData(int num) throws Exception {
		return hospitalMapper.getReadData(num);
	}


}
