package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Feedback;
import com.jewellery.model.OrderItem;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	List<Feedback> findByOrderItemIn(List<OrderItem> orderItems);

}
