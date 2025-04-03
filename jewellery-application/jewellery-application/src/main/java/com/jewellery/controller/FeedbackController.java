package com.jewellery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.dto.FeedbackDTO;
import com.jewellery.model.Feedback;
import com.jewellery.service.FeedbackServiceImpl;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
 
	@Autowired
	private FeedbackServiceImpl feedbackServiceImpl;

	
	
    @PostMapping("/add")
	public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
		Feedback createdFeedback = feedbackServiceImpl.submitFeedback(feedback);
		return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
	}
    

	@PutMapping("/update/{feedbackId}")
	public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable Long feedbackId, @RequestBody FeedbackDTO feedbackdto) {
		FeedbackDTO updatedFeedback = feedbackServiceImpl.updateFeedback(feedbackId, feedbackdto);
		return new ResponseEntity<>(updatedFeedback,HttpStatus.OK);
	}
	

	@GetMapping("/getfeedbacks")
	public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
		List<FeedbackDTO> feedbacks = feedbackServiceImpl.getAllFeedbacks();
		return new ResponseEntity<>(feedbacks,HttpStatus.OK);
	}
	

	@GetMapping("/feedbackbyid/{feedbackId}")
	public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long feedbackId) {
		FeedbackDTO feedback = feedbackServiceImpl.getFeedbackById(feedbackId);
		return new ResponseEntity<>(feedback,HttpStatus.OK);
	}
	
	
	@GetMapping("/feedbackbyproductid/{productId}")
	public ResponseEntity<List<Feedback>> getFeedbacksByProductId(@PathVariable Long productId) {
		List<Feedback> feedbacks = feedbackServiceImpl.getFeedbacksByProductId(productId);
		return new ResponseEntity<>(feedbacks,HttpStatus.OK);
	}
	


}




















