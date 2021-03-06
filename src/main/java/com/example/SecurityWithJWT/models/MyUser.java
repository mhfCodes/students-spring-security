package com.example.SecurityWithJWT.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class MyUser {

	
//	@SequenceGenerator(
//				name = "user_sequence",
//				sequenceName = "user_sequence",
//				allocationSize = 1				
//			)
//	@GeneratedValue(
//				strategy = GenerationType.SEQUENCE,
//				generator = "user_sequence"
//				)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(length = 250)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Collection<Role> roles = new ArrayList<>();
	
	private Boolean active;
	
	public MyUser() {
	}
	
	public MyUser(String username, String password, Collection<Role> roles, Boolean active) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "MyUser [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ ", active=" + active + "]";
	}
	
		
}
