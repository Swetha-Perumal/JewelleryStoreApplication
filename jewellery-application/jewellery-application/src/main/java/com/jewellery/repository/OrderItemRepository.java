package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.OrderItem;
import com.jewellery.model.Orders;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	 List<OrderItem> findByOrder(Orders order);
	 List<OrderItem> findByProduct_ProductId(Long productId);

}
