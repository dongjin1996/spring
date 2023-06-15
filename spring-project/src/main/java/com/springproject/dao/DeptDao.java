package com.springproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springproject.dto.Dept;

@Mapper
public interface DeptDao {
	public List<Dept> selectList(); 
}
