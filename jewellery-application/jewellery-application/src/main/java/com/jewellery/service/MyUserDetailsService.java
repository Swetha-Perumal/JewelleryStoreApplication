package com.jewellery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jewellery.model.UserPrincipal;
import com.jewellery.model.User;
import com.jewellery.repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService{

	
	
	@Autowired 
	private UserRepository userRepository;

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userSecurity=userRepository.findByUserName(username);
		if(userSecurity ==null) {
			System.out.println("User 404");
			throw new UsernameNotFoundException("User 404");
		}
		return new UserPrincipal(userSecurity);
	}
}
