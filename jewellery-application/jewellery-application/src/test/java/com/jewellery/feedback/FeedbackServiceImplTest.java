package com.jewellery.feedback;

import com.jewellery.dto.FeedbackDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Feedback;
import com.jewellery.model.OrderItem;
import com.jewellery.repository.FeedbackRepository;
import com.jewellery.repository.OrderItemRepository;
import com.jewellery.service.FeedbackServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderItemRepository orderItemRepository;

    private Feedback feedback;
    private FeedbackDTO feedbackDTO;

    @BeforeEach
    void setUp() {
        feedback = new Feedback();
        feedback.setFeedBackId(1L);
        feedback.setComment("Great product!");
        feedback.setRating(5);

        feedbackDTO = new FeedbackDTO();
        feedbackDTO.setComment("Excellent product!");
        feedbackDTO.setRating(4);
    }

    @Test
    void testSubmitFeedback_Success() {
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback result = feedbackService.submitFeedback(feedback);

        assertNotNull(result);
        assertEquals("Great product!", result.getComment());
        assertEquals(5, result.getRating());
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testUpdateFeedback_Success() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        FeedbackDTO updatedFeedback = feedbackService.updateFeedback(1L, feedbackDTO);

        assertNotNull(updatedFeedback);
        assertEquals("Excellent product!", updatedFeedback.getComment());
        assertEquals(4, updatedFeedback.getRating());

        verify(feedbackRepository, times(1)).findById(1L);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testUpdateFeedback_FeedbackNotFound() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> feedbackService.updateFeedback(1L, feedbackDTO));

        verify(feedbackRepository, times(1)).findById(1L);
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testGetAllFeedbacks_Success() {
        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback));
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        List<FeedbackDTO> feedbackList = feedbackService.getAllFeedbacks();

        assertNotNull(feedbackList);
        assertEquals(1, feedbackList.size());
        assertEquals("Excellent product!", feedbackList.get(0).getComment());

        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void testGetFeedbackById_Success() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        FeedbackDTO result = feedbackService.getFeedbackById(1L);

        assertNotNull(result);
        assertEquals("Excellent product!", result.getComment());

        verify(feedbackRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFeedbackById_NotFound() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> feedbackService.getFeedbackById(1L));

        verify(feedbackRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFeedbacksByProductId_Success() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(100L);

        when(orderItemRepository.findByProduct_ProductId(1L)).thenReturn(List.of(orderItem));
        when(feedbackRepository.findByOrderItemIn(List.of(orderItem))).thenReturn(List.of(feedback));

        List<Feedback> feedbacks = feedbackService.getFeedbacksByProductId(1L);

        assertNotNull(feedbacks);
        assertEquals(1, feedbacks.size());
        assertEquals("Great product!", feedbacks.get(0).getComment());

        verify(orderItemRepository, times(1)).findByProduct_ProductId(1L);
        verify(feedbackRepository, times(1)).findByOrderItemIn(anyList());
    }

}
