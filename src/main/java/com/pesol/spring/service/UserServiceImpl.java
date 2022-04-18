package com.pesol.spring.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pesol.spring.entity.Role;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.MyUserDetail;
import com.pesol.spring.model.RegistrationUser;
import com.pesol.spring.repository.RoleRepository;
import com.pesol.spring.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional // for get roles
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User tempUser = userRepository.findByUsername(username);
		
		if (tempUser == null) {
			throw new UsernameNotFoundException("User with username: " + username + " not exist");
		}
		
		return new MyUserDetail(
				tempUser.getId(), 
				tempUser.getUsername(), 
				tempUser.getPassword(), 
				tempUser.getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toSet()));
	}

	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(RegistrationUser registrationUser) {
		User tempUser = new User(
				registrationUser.getUsername(),
				passwordEncoder.encode(registrationUser.getPassword()),
				registrationUser.getFirstName(), 
				registrationUser.getLastName(), 
				registrationUser.getEmail());
		
		Role tempRole = roleRepository.findByName("ROLE_USER");
		tempUser.addRole(tempRole);
		
		tempUser = userRepository.save(tempUser);
		
		return tempUser;
	}

	@Override
	public User getByUsernameOrEmail(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findByRoleName("ROLE_USER");
	}

	@Override
	public User getById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
		
	}

	
}
