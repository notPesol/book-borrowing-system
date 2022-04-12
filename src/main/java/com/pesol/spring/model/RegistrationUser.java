package com.pesol.spring.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.pesol.spring.validation.FieldMatch;
import com.pesol.spring.validation.ValidEmail;


@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class RegistrationUser {

	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String username;

	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String matchingPassword;

	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;

	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;

	@ValidEmail
	@NotBlank(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;
	
	public RegistrationUser() {
	
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

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
		
}
