package com.example.SecurityWithJWT.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	
	public MyUser getLoggedInStudentData(Long id) {
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
		}
		
		if (password != null && password.length() > 0 && !passwordEncoder.matches(password, myUser.getPassword())) {
			myUser.setPassword(passwordEncoder.encode(password));
		}
		
		myUserRepo.save(myUser);
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
