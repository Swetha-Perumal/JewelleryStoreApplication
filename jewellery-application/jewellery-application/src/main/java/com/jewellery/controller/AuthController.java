package com.jewellery.controller;

import com.jewellery.dto.RegisterRequestDTO;
import com.jewellery.model.User;
import com.jewellery.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequestDTO request) {
        User user = authService.registerUser(request);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/signin")
   	public ResponseEntity<String> signIn(@RequestBody User user) {
    	String token = authService.signIn(user);
   		return ResponseEntity.ok(token);		
   	}
}

