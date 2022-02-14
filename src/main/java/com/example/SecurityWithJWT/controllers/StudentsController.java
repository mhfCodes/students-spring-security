package com.example.SecurityWithJWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecurityWithJWT.models.MyUser;
import com.example.SecurityWithJWT.service.MyUserService;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
	
	private MyUserService myUserService;
	
	@Autowired
	public StudentsController(MyUserService myUserService) {
		this.myUserService = myUserService;
	}

	
	// lets consider after user logs in
	// we send a JSON of MyUser Object to our client
	// and now on our client we have access to user's id
	// which we can send to our backend for further requests
	
	@GetMapping(path = "/{studentId}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public MyUser getInfo(@PathVariable("studentId") Long id) {
		return myUserService.getStudentData(id);
	}
	
	@PutMapping(path = "/{studentId}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public String updateUsernamePassword(@PathVariable("studentId") Long id,
										 @RequestParam("username") String username,
									     @RequestParam("password") String password) {	
		myUserService.updateUsernamePassword(id, username, password);
		return "Data Updated Successfully";
	}
	
	
	
}
