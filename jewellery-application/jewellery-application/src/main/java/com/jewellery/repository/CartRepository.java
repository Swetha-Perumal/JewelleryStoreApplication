package com.jewellery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Cart;import com.jewellery.model.Customer;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

	Cart findByCustomer_CustomerId(Long customerId);

	Optional<Customer> findByCustomer(Customer customer);

	@Query("SELECT MAX(c.cartId) FROM Cart c")
    Long findMaxCartId();


}
