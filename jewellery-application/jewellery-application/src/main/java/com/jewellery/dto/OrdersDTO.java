package com.jewellery.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.jewellery.model.OrderItem;
import com.jewellery.model.Payment;
import com.jewellery.Utils.OrderStatus;
import com.jewellery.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

	private Long orderId;
	private LocalDateTime OrderDate;
	private Double totalPrice;
	private OrderStatus status;
	private Customer customer;
	private AddressDTO shippingAddress;
	private List<OrderItem>orderItems;
	private Payment payment;
}
