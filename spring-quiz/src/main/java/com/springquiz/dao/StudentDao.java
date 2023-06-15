package com.springquiz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springquiz.dto.Student;

@Mapper
public interface StudentDao {
	public List<Student> selectList();
}
