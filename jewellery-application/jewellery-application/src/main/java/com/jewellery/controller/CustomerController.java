package com.jewellery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.CartDTO;
import com.jewellery.dto.CustomerDTO;
import com.jewellery.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
		CustomerDTO customerDTO = customerService.getCustomerById(customerId);
		return new ResponseEntity<>(customerDTO,HttpStatus.OK);
	}
	

	@PutMapping("/update")
	public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO userDTO) {
		CustomerDTO updatedCustomer = customerService.updateCustomer(userDTO);
		return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
	}
	

	@GetMapping("/cart/{customerId}")
	public ResponseEntity<CartDTO> getCartByCustomerId(@PathVariable Long customerId) {
		CartDTO cartDTO = customerService.getCartByCustomerId(customerId);
		return new ResponseEntity<>(cartDTO,HttpStatus.OK);
	}

	
	@GetMapping("/getAddressByCustomerId/{customerId}")
	public ResponseEntity<AddressDTO> getAddressByCustomerId(@PathVariable Long customerId, @RequestHeader("Authorization") String token) {
		AddressDTO addressDTO = customerService.getAddressByCustomerId(customerId);
		return new ResponseEntity<>(addressDTO,HttpStatus.OK);
	}

}
