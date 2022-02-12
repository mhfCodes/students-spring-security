package com.example.SecurityWithJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.SecurityWithJWT.service.MyUserService;

@Configuration
@EnableWebSecurity	
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin();
			
	}
	
}
