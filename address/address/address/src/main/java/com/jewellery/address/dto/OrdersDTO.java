package com.jewellery.address.dto;

import java.time.LocalDateTime;
import com.jewellery.address.util.OrderStatus;
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
	
	
}
