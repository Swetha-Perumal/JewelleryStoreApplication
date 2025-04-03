//package com.jewellery.address.feign;
//
//import java.util.List;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import com.jewellery.address.dto.OrdersDTO;
//
//@FeignClient(name = "ORDER-SERVICE", path = "/api/orders")
//public interface OrderClient {
//    @GetMapping("/address/{addressId}")
//    List<OrdersDTO> getOrdersByAddressId(@PathVariable("addressId") Long addressId);
//}
