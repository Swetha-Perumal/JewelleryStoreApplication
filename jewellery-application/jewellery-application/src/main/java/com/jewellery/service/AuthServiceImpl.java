package com.jewellery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jewellery.Utils.Role;
import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.RegisterRequestDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.feign.AddressClient;
import com.jewellery.model.Cart;
import com.jewellery.model.Customer;
import com.jewellery.model.User;
import com.jewellery.repository.CartRepository;
import com.jewellery.repository.CustomerRepository;
import com.jewellery.repository.UserRepository;
import com.jewellery.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;


@Service
public class AuthServiceImpl implements AuthService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AddressClient addressClient;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private AuthenticationManager authenticationManager;
   

	public User registerUser(RegisterRequestDTO request) {
		
		
	    User user = new User();
	    user.setUserName(request.getUserName());
	    user.setPassword(encoder.encode(request.getPassword()));
	    user.setEmail(request.getEmail());
	    user.setRole(request.getRole());
 
	    user = userRepository.save(user);
 
	    if (request.getRole() == Role.CUSTOMER || request.getRole() == Role.VENDOR) {
	        Customer customer = new Customer();
	        customer.setFirstName(request.getFirstName());
	        customer.setLastName(request.getLastName());
	        customer.setMobileNumber(request.getMobileNumber());
	        if(request.getRole() == Role.CUSTOMER) {
	            Long maxCartId = cartRepository.findMaxCartId();
	            Long newCartId = (maxCartId == null) ? 1L : maxCartId + 1;
 
	            Cart cart = new Cart();
	            cart.setCustomer(customer);
	            cart.setCartId(newCartId);
	            cart.setCartItems(List.of());
	            customer.setCart(cart);
	        }
	        
	        customer.setUser(user);
	        
	        String token = "Bearer " + jwtService.generateToken(user.getUserName());
	        if (request.getAddressId() != null) {
	            try {
	                AddressDTO addressDTO = addressClient.getAddressById(request.getAddressId(),token);
	                if (addressDTO != null) {
	                    customer.setAddressId(addressDTO.getAddressId());  
	                }
	            } catch (Exception e) {
	                throw new ResourceNotFoundException("Address service unavailable: " + e.getMessage());
	            }
	        }
	        customer = customerRepository.save(customer);
	        user.setCustomer(customer);
	        userRepository.save(user);
	    }
	    return user;
	}
	
	

	@Override
	public String signIn(User user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUserName());
		}
		else {
			return "Login Failed";
		}
	}

}
