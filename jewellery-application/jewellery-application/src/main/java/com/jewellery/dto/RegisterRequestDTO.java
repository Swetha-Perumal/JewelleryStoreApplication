package com.jewellery.dto;

import com.jewellery.Utils.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String userName;
    private String password;
    private String email;
    private Role role;

    // Only needed for CUSTOMER role
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private Long addressId;
}
