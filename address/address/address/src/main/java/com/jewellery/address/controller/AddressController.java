package com.jewellery.address.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jewellery.address.dto.AddressDTO;
import com.jewellery.address.model.Address;
import com.jewellery.address.service.AddressServiceImpl;

//@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressServiceImpl addressServiceImpl;
	
	@PostMapping("/add")
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		 Address createdAddress = addressServiceImpl.createAddress(address);
	     return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
	}

	@GetMapping("/findbyid/{addressId}")
	public AddressDTO getAddressById(@PathVariable Long addressId) {
       return addressServiceImpl.getAddressById(addressId);
	}
	@PutMapping("/update/{addressId}")
	public AddressDTO updateAddress(@PathVariable Long addressId,@RequestBody AddressDTO addressDTO) {
		return addressServiceImpl.updateAddress(addressId, addressDTO);
	}
	@GetMapping("/getall")
	public List<AddressDTO> getAllAddresses() {
		return addressServiceImpl.getAllAddresses();
	}
	@DeleteMapping
	("/delete/{addressId}")
	public void deleteAddress(@PathVariable Long addressId) {
		addressServiceImpl.deleteAddress(addressId);;
	}
}
