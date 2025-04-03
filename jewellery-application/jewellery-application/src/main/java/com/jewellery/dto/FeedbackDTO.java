package com.jewellery.dto;

import com.jewellery.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

	private Long feedBackId;
	private String Comment;
	private int rating;
	private OrderItem orderitem;
	
}
