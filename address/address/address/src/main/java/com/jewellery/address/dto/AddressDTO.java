package com.jewellery.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
