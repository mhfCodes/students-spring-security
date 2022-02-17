package com.example.SecurityWithJWT.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecurityWithJWT.models.MyUser;
import com.example.SecurityWithJWT.service.MyUserService;

@RestController
@RequestMapping("/management/api/students")
public class AdminsController {
	
	private MyUserService myUserService;
	
	@Autowired
	public AdminsController(MyUserService myUserService) {
		this.myUserService = myUserService;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Set<MyUser> getAllStudentsAndTrainees() {
		return myUserService.getAllStudentsAndTrainees();
	}
	
	@GetMapping(path = "/{studentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MyUser getStudentInfo(@PathVariable("studentId") Long id) {
		return myUserService.getStudentData(id);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addStudentOrTrainee(@RequestBody MyUser studentOrTrainee) {
		myUserService.addStudentOrTrainee(studentOrTrainee);
		return "Student Saved Successfully";
	}
	
	@PutMapping(path = "/{studentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateUsernamePassword(@PathVariable("studentId") Long id,
										 @RequestParam("username") String username,
									     @RequestParam("password") String password) {	
		myUserService.updateUsernamePassword(id, username, password);
		return "Data Updated Successfully";
	}
	
	@PutMapping(path = "/active/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	public String updateActiveStatus(@PathVariable("studentId") Long id,
									 @RequestParam("active") Boolean active) {
		myUserService.updateActiveStatus(id, active);
		return "Active Status Updated Successfully";
	}
	
	@DeleteMapping(path = "/{studentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteStudentOrTrainee(@PathVariable("studentId") Long id) {
		myUserService.deleteStudentOrTrainee(id);
		return "User Deleted Successfully";
	}
	
}
