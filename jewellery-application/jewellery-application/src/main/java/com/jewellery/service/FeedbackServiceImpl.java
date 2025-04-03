package com.jewellery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.dto.FeedbackDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Feedback;
import com.jewellery.model.OrderItem;
import com.jewellery.repository.FeedbackRepository;
import com.jewellery.repository.OrderItemRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService{
 
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired	
	private ModelMapper modelMapper;
	
	@Autowired 
	private OrderItemRepository orderItemRepository;
	
	@Override
	public Feedback submitFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}
	
	
	@Override
	public FeedbackDTO updateFeedback(Long feedbackId, FeedbackDTO feedbackdto) {
		Feedback existingFeedback = feedbackRepository.findById(feedbackId)
	              .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
	      existingFeedback.setComment(feedbackdto.getComment());
	      existingFeedback.setRating(feedbackdto.getRating());
	      Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
	      return modelMapper.map(updatedFeedback,FeedbackDTO.class);
	}
	
	
	@Override
	public List<FeedbackDTO> getAllFeedbacks() {
		List<Feedback> feedbacks = feedbackRepository.findAll();
	     return feedbacks.stream()
	        		.map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
	                .collect(Collectors.toList());
	}
	
	@Override
	public FeedbackDTO getFeedbackById(Long feedbackId) {
		 Feedback feedback = feedbackRepository.findById(feedbackId)
	              .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
	          return modelMapper.map(feedback,FeedbackDTO.class);

	}
	
	
	public List<Feedback> getFeedbacksByProductId(Long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProduct_ProductId(productId);
        return feedbackRepository.findByOrderItemIn(orderItems);
    }
	

	
}













//@Override
//public List<FeedbackDTO> getFeedbacksByProductId(Long productId) {
//	List<Feedback> feedbacks=(List<Feedback>) feedbackRepository.findByProduct_ProductId(productId);
//	return feedbacks.stream()
//          .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class