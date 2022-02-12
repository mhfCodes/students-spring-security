package com.example.SecurityWithJWT.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/students")
public class AdminsController {

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String getStudents() {
		return "Only Admins Can Access This API";
	}
	
}
