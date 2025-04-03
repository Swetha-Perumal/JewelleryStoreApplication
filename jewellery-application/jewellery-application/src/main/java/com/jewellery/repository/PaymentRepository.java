package com.jewellery.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jewellery.Utils.PaymentStatus;
import com.jewellery.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	
	 List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
	 
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
     List<Payment> findByPaymentDateRange(Date startDate, Date endDate);
 
     Payment findByOrders_OrderId(Long orderId);

    @Query("SELECT MAX(p.paymentId) FROM Payment p")
     Long findMaxPaymentId();

}
