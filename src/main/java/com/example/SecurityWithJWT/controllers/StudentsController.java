package com.example.SecurityWithJWT.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

	@GetMapping
	public String getStudents() {
		return "Students And Admins Can Access This API";
	}
	
	
}
