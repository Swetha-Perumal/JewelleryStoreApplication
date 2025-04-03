package com.jewellery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
	
	@Id
	private Long feedBackId;
	private String comment;
	private int rating;
	
	
	@OneToOne
	@JoinColumn(name = "orderitem_id")
	private OrderItem orderItem;
}
