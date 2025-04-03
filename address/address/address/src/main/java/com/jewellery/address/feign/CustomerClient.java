//package com.jewellery.address.feign;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import com.jewellery.address.dto.CustomerDTO;
//
//@FeignClient(name = "USER-SERVICE", path = "/api/users")
//public interface CustomerClient {
//    @GetMapping("/{id}")
//    CustomerDTO getUserById(@PathVariable("id") Long id);
//}
