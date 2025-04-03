package com.jewellery.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewellery.address.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

}
