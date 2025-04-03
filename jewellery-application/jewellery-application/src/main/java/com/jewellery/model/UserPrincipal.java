package com.jewellery.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{

	
	private final User userSecurity;

	public UserPrincipal(User userSecurity) {
		this.userSecurity=userSecurity;
	}
	
	

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+ userSecurity.getRole().name()));
	}


	@Override
	public String getPassword() {
		return userSecurity.getPassword();
	}


	@Override
	public String getUsername() {
		return userSecurity.getUserName();
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true;  // Change this based on your business logic
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Change this based on your business logic
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Change this based on your business logic
    }
 
    @Override
    public boolean isEnabled() {
        return true;  // You can use a field like `user.isActive()` if applicable
    }
}


