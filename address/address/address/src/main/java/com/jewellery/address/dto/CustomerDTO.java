package com.jewellery.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Long customerId;
	private String firstName;
	private String lastName;
	private String mobileNumber;


}
