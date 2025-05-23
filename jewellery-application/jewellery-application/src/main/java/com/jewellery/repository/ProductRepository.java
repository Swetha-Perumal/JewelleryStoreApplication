package com.jewellery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	 List<Product> findByCategory_CategoryName(String categoryName);

}
