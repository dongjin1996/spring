package com.springquiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springquiz.dao.StudentDao;
import com.springquiz.dto.Student;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentDao studentdao;
	
	@Override
	public List<Student> selectList() {
		return studentdao.selectList();
	}
}
