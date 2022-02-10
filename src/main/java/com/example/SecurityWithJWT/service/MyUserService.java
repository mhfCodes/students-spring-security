package com.example.SecurityWithJWT.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SecurityWithJWT.models.MyUser;
import com.example.SecurityWithJWT.models.MyUserDetails;
import com.example.SecurityWithJWT.repository.MyUserRepository;
import com.example.SecurityWithJWT.repository.RoleRepository;

@Service
public class MyUserService implements UserDetailsService {
	
	private final MyUserRepository myUserRepo;
	private final RoleRepository roleRepo;
	
	@Autowired
	public MyUserService(MyUserRepository myUserRepo, RoleRepository roleRepo) {
		this.myUserRepo = myUserRepo;
		this.roleRepo = roleRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MyUser myUser = myUserRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username" + username + "Does Not Exist"));
		
		Set<SimpleGrantedAuthority> myRoles = myUser.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		
		MyUserDetails myUserDetails = new MyUserDetails(myUser.getUsername(), myUser.getPassword(), myRoles, myUser.isActive());
		return myUserDetails;
		
	}
	
}
