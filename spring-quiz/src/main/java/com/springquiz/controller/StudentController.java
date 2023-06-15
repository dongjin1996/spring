package com.springquiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springquiz.dto.Student;
import com.springquiz.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentservice;
	
	@GetMapping("/student")
	public List<Student> main() {
		List<Student> list = studentservice.selectList();
		return list;
	}
}
