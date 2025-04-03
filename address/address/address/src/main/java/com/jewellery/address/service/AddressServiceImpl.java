package com.jewellery.address.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.address.dto.AddressDTO;
import com.jewellery.address.exception.ResourceNotFoundException;
import com.jewellery.address.model.Address;
import com.jewellery.address.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
        
       return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addresses = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));

        addresses.setFirstName(addressDTO.getFirstName());
        addresses.setLastName(addressDTO.getLastName());
        addresses.setStreetAddress(addressDTO.getStreetAddress());
        addresses.setCity(addressDTO.getCity());
        addresses.setState(addressDTO.getState());
        addresses.setMobile(addressDTO.getMobile());

        Address updatedAddress = addressRepository.save(addresses);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
        addressRepository.delete(address);
    }
}
