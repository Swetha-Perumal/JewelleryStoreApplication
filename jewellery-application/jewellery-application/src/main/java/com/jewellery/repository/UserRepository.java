package com.jewellery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewellery.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 User findByUserName(String username);
	

}
