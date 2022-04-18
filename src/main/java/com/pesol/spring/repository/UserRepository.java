package com.pesol.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

import com.pesol.spring.entity.Role;
import com.pesol.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);

	@Query("FROM User u WHERE u.username = ?1 OR u.email = ?2")
	User findByUsernameOrEmail(String username, String email);


	@Query("FROM User u JOIN u.roles r WHERE r.name = ?1")
	List<User> findByRoleName(String roleName);
}
