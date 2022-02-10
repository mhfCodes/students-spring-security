package com.example.SecurityWithJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/management/**").hasRole("ADMIN")
			.antMatchers("/api/students").hasAnyRole("STUDENT", "ADMIN")
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails john = User.builder()
				.username("john")
				.password(passwordEncoder.encode("1234"))
				.roles("STUDENT").build();
		
		UserDetails alex = User.builder()
				.username("alex")
				.password(passwordEncoder.encode("1234"))
				.roles("ADMIN").build();

		return new InMemoryUserDetailsManager(
					john,
					alex
				);				
		
	}

	
	
}
