package com.pesol.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pesol.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);

	@Query("FROM User u WHERE u.username = ?1 OR u.email = ?2")
	User findByUsernameOrEmail(String username, String email);
}
