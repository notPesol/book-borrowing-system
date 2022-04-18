package com.pesol.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import com.pesol.spring.entity.User;
import com.pesol.spring.model.RegistrationUser;

public interface UserService extends UserDetailsService{
	
	User getByUsername(String username);
	
	User getByUsernameOrEmail(String username, String email);

	User save(RegistrationUser registrationUser);

	void delete(User user);

	List<User> getAll();

	User getById(int id);

	void update(User user);
}
