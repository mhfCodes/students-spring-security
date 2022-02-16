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
public class Role {

//	@SequenceGenerator(
//				name = "role_sequence",
//				sequenceName = "role_sequence",
//				allocationSize = 1
//			)
//	@GeneratedValue(
//				strategy = GenerationType.SEQUENCE,
//				generator = "role_sequence"
//			)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String roleName;
	
	public Role() {
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}

}

