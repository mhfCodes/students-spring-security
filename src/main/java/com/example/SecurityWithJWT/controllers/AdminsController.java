package com.example.SecurityWithJWT.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/students")
public class AdminsController {

	@GetMapping
	public String getStudents() {
		return "Only Admins Can Access This API";
	}
	
}
