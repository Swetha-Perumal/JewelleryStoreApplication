package com.jewellery.dto;

import java.util.List;

import com.jewellery.model.Orders;
import com.jewellery.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {


	private Long addressId;
	private String firstName;
	private String lastName;
	private Long mobile;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private Customer user;
    private List<Orders> orders;
}
