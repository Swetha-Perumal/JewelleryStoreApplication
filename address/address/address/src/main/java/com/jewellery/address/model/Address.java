package com.jewellery.address.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id
	private Long addressId;
	private String firstName;
	private String lastName;
	private Long mobile;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    
    
//    
//    @ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "shippingAddress" , cascade = CascadeType.ALL)
//    private List<Orders> orders;

    
    
}
