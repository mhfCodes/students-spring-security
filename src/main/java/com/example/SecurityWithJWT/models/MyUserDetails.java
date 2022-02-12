package com.example.SecurityWithJWT.models;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private Set<SimpleGrantedAuthority> authorities;
	private Boolean active;
	
	public MyUserDetails(String username, String password, Set<SimpleGrantedAuthority> role, Boolean active) {
		this.username = username;
		this.password = password;
		this.authorities = role;
		this.active = active;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	@Override
	public String toString() {
		return "MyUserDetails [username=" + username + ", password=" + password + ", authorities=" + authorities
				+ ", active=" + active + "]";
	}	

}
