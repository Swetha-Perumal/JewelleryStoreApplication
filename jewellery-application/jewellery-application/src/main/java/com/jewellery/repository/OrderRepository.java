package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jewellery.Utils.OrderStatus;
import com.jewellery.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {



	 List<Orders> findByCustomer_CustomerId(Long customerId);

	 List<Orders> findByStatus(OrderStatus status);

	@Query("SELECT MAX(o.orderId) FROM Orders o")
	 Long findMaxOrderId();


}
