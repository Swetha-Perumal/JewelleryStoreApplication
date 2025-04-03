package com.jewellery.dto;

import java.util.Date;

import com.jewellery.Utils.PaymentStatus;
import com.jewellery.model.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private long paymentId;
  	private String paymentMethod;
  	private PaymentStatus paymentStatus;
  	private double paymentAmount;
  	private Date paymentDate;
    private Orders orders;
}
