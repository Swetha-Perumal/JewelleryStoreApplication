package com.jewellery.repository;

import com.jewellery.model.Customer;

public interface UserSecurityRepository {

	 Customer findByEmail(String email);

}
