package com.jewellery.service;

import com.jewellery.dto.RegisterRequestDTO;
import com.jewellery.model.User;

public interface AuthService {
	
	public User registerUser(RegisterRequestDTO request);
	
	public String signIn(User user);
}
