package com.jewellery.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.dto.PaymentDTO;
import com.jewellery.model.Payment;
import com.jewellery.service.PaymentServiceImpl;

@RestController
@RequestMapping("/payment")
public class PaymentController {
 
	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	
  

    @GetMapping("/getall")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentServiceImpl.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    

    @GetMapping("/findbyid/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
        PaymentDTO payment = paymentServiceImpl.getPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    


    @GetMapping("/between/{startDate}/{endDate}")
    public ResponseEntity<List<Payment>> getPaymentsByDateRange(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Payment> payments = paymentServiceImpl.getPaymentsByDateRange(startDate, endDate);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    

    @GetMapping("/{ordersId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long ordersId) {
        Payment payment = paymentServiceImpl.getPaymentByOrderId(ordersId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    

    @PostMapping("/pay/{orderId}/{method}")
    public ResponseEntity<Payment> makePayment(@PathVariable Long orderId, @PathVariable String method) {
        Payment payment = paymentServiceImpl.makePayment(orderId, method);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
}

































//@PutMapping("/update/{paymentId}")
//public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentDTO paymentDTO) {
//  PaymentDTO updatedPayment = paymentServiceImpl.updatePayment(paymentId, paymentDTO);
//  return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
//}
//
//
//@DeleteMapping("/delete/{paymentId}")
//public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
//  paymentServiceImpl.deletePayment(paymentId);
//  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//}
//
//
//@GetMapping("/status/{status}")
//public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
//  List<Payment> payments = paymentServiceImpl.getPaymentsByStatus(status);
//  return new ResponseEntity<>(payments, HttpStatus.OK);
//}


 