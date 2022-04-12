package com.pesol.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pesol.spring.entity.User;
import com.pesol.spring.model.RegistrationUser;

public interface UserService extends UserDetailsService{
	
	User getByUsername(String username);
	
	User getByUsernameOrEmail(String username, String email);

	User save(RegistrationUser registrationUser);

}
