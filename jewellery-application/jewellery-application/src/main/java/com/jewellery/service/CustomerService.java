package com.jewellery.service;

import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.CartDTO;
import com.jewellery.dto.CustomerDTO;

public interface CustomerService {

	
	public CustomerDTO updateCustomer(CustomerDTO customerDTO);
	
	public CustomerDTO getCustomerById(Long customerId);
	
	public CartDTO getCartByCustomerId(Long customerrId);
	
	//feign checking purpose
	public AddressDTO getAddressByCustomerId(Long customerrId);
}
