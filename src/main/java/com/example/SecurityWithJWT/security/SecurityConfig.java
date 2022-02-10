package com.example.SecurityWithJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.SecurityWithJWT.service.MyUserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final MyUserService myUserService;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder, MyUserService myUserService) {
		this.passwordEncoder = passwordEncoder;
		this.myUserService = myUserService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/management/**").hasRole("ROLE_ADMIN")
			.antMatchers("/api/students").hasAnyRole("ROLE_STUDENT", "ROLE_ADMIN")
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin();
	}
	
}
