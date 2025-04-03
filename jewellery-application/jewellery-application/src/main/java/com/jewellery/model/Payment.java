package com.jewellery.model;

import java.util.Date;

import com.jewellery.Utils.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
 
	@Id
	private long paymentId;
	
	private String paymentMethod;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private double paymentAmount;
	private Date paymentDate;
	
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Orders orders;
	
	

 
}
