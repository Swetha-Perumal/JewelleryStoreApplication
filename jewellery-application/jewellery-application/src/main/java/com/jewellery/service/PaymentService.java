package com.jewellery.service;

import java.util.Date;
import java.util.List;
import com.jewellery.dto.PaymentDTO;
import com.jewellery.model.Payment;

public interface PaymentService {
	 

	public Payment makePayment(Long orderId, String paymentMethod);
	
	public List<PaymentDTO> getAllPayments();
	
	public PaymentDTO getPaymentById(Long paymentId);
	
	public List<Payment> getPaymentsByDateRange(Date startDate, Date endDate);
	
	public Payment getPaymentByOrderId(Long ordersId);
	
	
}
