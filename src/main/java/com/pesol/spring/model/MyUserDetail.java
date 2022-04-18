package com.pesol.spring.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUserDetail extends User {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	public MyUserDetail(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName()).append(" [");
		sb.append("Id=").append(getId()).append(", ");
		sb.append("Username=").append(getUsername()).append(", ");
		sb.append("Password=[PROTECTED], ");
		sb.append("Granted Authorities=").append(getAuthorities()).append("]");
		return sb.toString();
	}
	
	

}
