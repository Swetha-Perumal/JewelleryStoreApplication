package com.jewellery.address.service;

import java.util.List;
import com.jewellery.address.dto.AddressDTO;
import com.jewellery.address.model.Address;

 
public interface AddressService {
 
	public Address createAddress(Address address);
	public AddressDTO getAddressById(Long addressId);
	public AddressDTO updateAddress(Long addressId,AddressDTO addressDTO);
	public List<AddressDTO> getAllAddresses();
	public void deleteAddress(Long addressId);
	}