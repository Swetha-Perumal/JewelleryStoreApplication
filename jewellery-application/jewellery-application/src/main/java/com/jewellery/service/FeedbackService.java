package com.jewellery.service;

import java.util.List;

import com.jewellery.dto.FeedbackDTO;
import com.jewellery.model.Feedback;

public interface FeedbackService {
	 
	public Feedback submitFeedback(Feedback feedback);
	
	public FeedbackDTO updateFeedback(Long feedbackId,FeedbackDTO feedbackdto);
	
	public List<FeedbackDTO> getAllFeedbacks();
	
	public FeedbackDTO getFeedbackById( Long feedbackId);
	
	public List<Feedback> getFeedbacksByProductId(Long productId);
	
	
	
}