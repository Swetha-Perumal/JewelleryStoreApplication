package com.jewellery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.jewellery.dto.AddressDTO;

@FeignClient(name = "address-service")
public interface AddressClient {

	
    @GetMapping("/address/findbyid/{addressId}")
    public AddressDTO getAddressById(@PathVariable Long addressId,@RequestHeader("Authorization")String token);
    
}
