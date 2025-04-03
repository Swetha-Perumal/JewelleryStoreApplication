package com.jewellery.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	@NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	private String lastName;
	
	
	@Column(unique = true, nullable = false)
    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
	private String mobileNumber;
	
	private Long addressId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
	private List<Product> products ;
	
	@JsonIgnore
	@OneToOne(mappedBy = "customer" ,cascade = CascadeType.ALL)
	private Cart cart;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Orders> order;
	

	@JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id")
    private User user;
}
