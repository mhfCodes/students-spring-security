package com.example.SecurityWithJWT;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.SecurityWithJWT.models.MyUser;
import com.example.SecurityWithJWT.models.Role;
import com.example.SecurityWithJWT.repository.MyUserRepository;
import com.example.SecurityWithJWT.repository.RoleRepository;

@SpringBootApplication
public class SecurityWithJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityWithJwtApplication.class, args);
	}
		
	@Bean
	CommandLineRunner run(MyUserRepository userRepo, RoleRepository roleRepo) {
		return args -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			Role STUDENT = new Role("ROLE_STUDENT");
			Role ADMIN = new Role("ROLE_ADMIN");
			Role ADMIN_TRAINEE = new Role("ROLE_ADMIN_TRAINEE");
			
			MyUser john = new MyUser("john007", encoder.encode("1234"), List.of(STUDENT), true);
			MyUser alex = new MyUser("alex007", encoder.encode("1234"), List.of(STUDENT), false);
			MyUser david = new MyUser("david007", encoder.encode("1234"), List.of(ADMIN), true);
			MyUser jason = new MyUser("jason007", encoder.encode("1234"), List.of(ADMIN_TRAINEE), true);
			MyUser sara = new MyUser("sara007", encoder.encode("1234"), List.of(STUDENT, ADMIN_TRAINEE), true);
			MyUser alice = new MyUser("alice007", encoder.encode("1234"), List.of(ADMIN), false);
			
			roleRepo.saveAll(List.of(STUDENT, ADMIN, ADMIN_TRAINEE));
			userRepo.saveAll(List.of(john, alex, david, jason, sara, alice));			
			
		};
	}

}
