package com.jewellery.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.CartDTO;
import com.jewellery.dto.CustomerDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.feign.AddressClient;
import com.jewellery.model.Customer;
import com.jewellery.repository.CustomerRepository;
import com.jewellery.repository.UserRepository;
import com.jewellery.security.JwtService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
 
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	AddressClient addressClient;
	
	@Autowired
	JwtService jwtService;
	
	 @Override
	    public AddressDTO getAddressByCustomerId(Long customerId) {
		 Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("User not found"));;
		 String token = "Bearer " + jwtService.generateToken(customer.getUser().getUserName());
	        return addressClient.getAddressById(customerId, token);
	    }

	@Override
    public CustomerDTO updateCustomer(CustomerDTO userDTO) {
    	Customer customer = customerRepository.findById(userDTO.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	customer.setFirstName(userDTO.getFirstName());
    	customer.setLastName(userDTO.getLastName());
    	customer.setMobileNumber(userDTO.getMobileNumber());
        Customer updatedCustomer = customerRepository.save(customer);
        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

	@Override
	public CustomerDTO getCustomerById(Long customerId) {
	    Customer customer =customerRepository.findById(customerId).get();
//	    if (user == null) {
//	        throw new ResourceNotFoundException("User with email " + email + " not found");
//	    }
	    return modelMapper.map(customer, CustomerDTO.class);
	}

	@Override
	public CartDTO getCartByCustomerId(Long userId) {
		Customer user = customerRepository.findById(userId).get();
		return modelMapper.map(user.getCart(), CartDTO.class);
	}


}