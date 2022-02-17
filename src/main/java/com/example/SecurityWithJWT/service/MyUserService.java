package com.example.SecurityWithJWT.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SecurityWithJWT.models.MyUser;
import com.example.SecurityWithJWT.models.MyUserDetails;
import com.example.SecurityWithJWT.repository.MyUserRepository;
import com.example.SecurityWithJWT.repository.RoleRepository;

@Service
@Transactional
public class MyUserService implements UserDetailsService {
	
	private final MyUserRepository myUserRepo;
	private final RoleRepository roleRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public MyUserService(MyUserRepository myUserRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
		this.myUserRepo = myUserRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public MyUser getStudentData(Long id) {
		MyUser myUser = myUserRepo.findById(id).orElseThrow(() -> new IllegalStateException("Can not find id"));
		return myUser;
	}
	
	public void updateUsernamePassword(Long id, String username, String password) {
		MyUser myUser = myUserRepo.findById(id)
				.orElseThrow(() -> new IllegalStateException("Can not find id"));
		
		if (username != null && username.length() > 0 && !Objects.equals(myUser.getUsername(), username)) {
			
			Optional<MyUser> student = myUserRepo.findByUsername(username);
			if (student.isPresent()) {
				throw new IllegalStateException("Username already taken");
			}
			
			myUser.setUsername(username);
		} else {
			throw new IllegalStateException("Username Either Does Not Exist Or Is The Same As Before");
		}
		
		if (password != null && password.length() > 0 && !passwordEncoder.matches(password, myUser.getPassword())) {
			myUser.setPassword(passwordEncoder.encode(password));
		} else {
			throw new IllegalStateException("Password Either Does Not Exist Or Is The Same As Before");
		}
		
		myUserRepo.save(myUser);
	}
	
	public Set<MyUser> getAllStudentsAndTrainees() {
		List<MyUser> allUsers = myUserRepo.findAll();
		Set<MyUser> studentsAndTrainees = new HashSet<>();
		
		allUsers.forEach(user -> {
			user.getRoles().forEach(role -> {
				if (role.getRoleName().equals("ROLE_STUDENT") || role.getRoleName().equals("ROLE_ADMIN_TRAINEE")) {
					studentsAndTrainees.add(user);
				}
			});
		});
		return studentsAndTrainees;
	}
	
	public void addStudentOrTrainee(MyUser studentOrTrainee) {
		studentOrTrainee.setPassword(passwordEncoder.encode(studentOrTrainee.getPassword()));
		myUserRepo.save(studentOrTrainee);
	}
	
	public void updateActiveStatus(Long id, Boolean active) {
		MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUser studentOrAdminTrainee = myUserRepo.findById(id).orElseThrow(() -> new IllegalStateException("Can Not Find Id"));
			
		myUser.getAuthorities().forEach(role -> {
			
			if (role.getAuthority().equals("ROLE_ADMIN")) {
				
				studentOrAdminTrainee.setActive(active);
			} else if (role.getAuthority().equals("ROLE_ADMIN_TRAINEE")) {
				
				studentOrAdminTrainee.getRoles().forEach(myRole -> {
					
					if (myRole.getRoleName().equals("ROLE_STUDENT")) {
						studentOrAdminTrainee.setActive(active);
					} else {
						throw new IllegalStateException("Admin Trainee Can Only Change Students Active Status");
					}
					
				});
			}
			
		});	
		myUserRepo.save(studentOrAdminTrainee);

	}
	
	public void deleteStudentOrTrainee(Long id) {
		MyUser studentOrAdminTrainee = myUserRepo.findById(id).orElseThrow(() -> new IllegalStateException("Can Not Find id"));
		
		studentOrAdminTrainee.getRoles().forEach(role -> {
			if (role.getRoleName().equals("ROLE_ADMIN")) {
				throw new IllegalStateException("Admin Can Not Delete Admin");
			}
		});
		
		myUserRepo.deleteById(id);	
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MyUser myUser = myUserRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username" + username + "Does Not Exist"));
		
		Set<SimpleGrantedAuthority> myRoles = myUser.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		
		MyUserDetails myUserDetails = new MyUserDetails(myUser.getUsername(), myUser.getPassword(), myRoles, myUser.isActive());
		System.out.println(myUserDetails.toString());
		return myUserDetails;
		
	}
	
}
