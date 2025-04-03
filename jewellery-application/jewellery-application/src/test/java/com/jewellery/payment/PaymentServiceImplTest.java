package com.jewellery.payment;

import com.jewellery.Utils.PaymentStatus;
import com.jewellery.dto.PaymentDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.*;
import com.jewellery.repository.*;
import com.jewellery.service.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ModelMapper modelMapper;

    private Orders order;
    private Payment payment;
    private Cart cart;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        order = new Orders();
        order.setOrderId(1L);
        order.setTotalPrice(100.0);
        order.setCustomer(new Customer());

        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setOrders(order);
        payment.setPaymentMethod("Credit Card");
        payment.setPaymentAmount(100.0);
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);

        product = new Product();
        product.setProductId(1L);
        product.setQuantity(10);

        orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(50.0);
        orderItem.setDeliveryDate(LocalDateTime.now().plusDays(5));

        cart = new Cart();
        cart.setCartItems(new ArrayList<>());
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(payment));
        when(modelMapper.map(any(Payment.class), eq(PaymentDTO.class))).thenReturn(new PaymentDTO());

        List<PaymentDTO> payments = paymentService.getAllPayments();

        assertEquals(1, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetPaymentById_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(modelMapper.map(payment, PaymentDTO.class)).thenReturn(new PaymentDTO());

        PaymentDTO foundPayment = paymentService.getPaymentById(1L);

        assertNotNull(foundPayment);
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPaymentById_Failure() {
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentById(2L));

        assertEquals("Address not found with id 2", exception.getMessage());
    }

    @Test
    void testGetPaymentsByDateRange() {
        Date startDate = new Date();
        Date endDate = new Date();
        when(paymentRepository.findByPaymentDateRange(startDate, endDate)).thenReturn(Collections.singletonList(payment));

        List<Payment> payments = paymentService.getPaymentsByDateRange(startDate, endDate);

        assertEquals(1, payments.size());
        verify(paymentRepository, times(1)).findByPaymentDateRange(startDate, endDate);
    }

    @Test
    void testGetPaymentByOrderId() {
        when(paymentRepository.findByOrders_OrderId(1L)).thenReturn(payment);

        Payment foundPayment = paymentService.getPaymentByOrderId(1L);

        assertNotNull(foundPayment);
        assertEquals(1L, foundPayment.getPaymentId());
        verify(paymentRepository, times(1)).findByOrders_OrderId(1L);
    }

    @Test
    void testMakePayment_Success() {
        // Mock order retrieval
        Orders order = new Orders();
        order.setOrderId(1L);
        order.setTotalPrice(100.0); // Total amount to be paid

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        
        // Mock payment ID generation
        when(paymentRepository.findMaxPaymentId()).thenReturn(1L);

        // Mock payment saving
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            savedPayment.setPaymentId(2L); // Simulate DB-assigned ID
            return savedPayment;
        });

        // Mock cart retrieval
        Customer customer = new Customer();
        customer.setCustomerId(10L);
        order.setCustomer(customer);

        Cart cart = new Cart();
        cart.setCustomer(customer);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setProductId(5L);
        product.setQuantity(10); // Available stock
        product.setPrice(50.0);  // Product price

        cartItem.setProduct(product);
        cartItem.setQuantity(2);  // Buying 2 items
        cartItem.setTotalPrice(100.0); // 2 * 50 = 100
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        when(cartRepository.findByCustomer_CustomerId(10L)).thenReturn(cart);

        // Mock product repository
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Mock order item repository
        when(orderItemRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock order saving
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        // Mock cart saving
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Execute the method under test
        Payment resultPayment = paymentService.makePayment(1L, "Credit Card");

        // Assertions
        assertNotNull(resultPayment);
        assertEquals(PaymentStatus.COMPLETED, resultPayment.getPaymentStatus());
        assertEquals("Credit Card", resultPayment.getPaymentMethod());
        assertEquals(100.0, resultPayment.getPaymentAmount()); // Ensure total price is correctly set

        // Verify interactions
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Orders.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
        verify(cartRepository, times(1)).save(any(Cart.class));

        // Ensure product quantity is updated correctly
        assertEquals(8, product.getQuantity()); // 10 - 2 = 8
    }


    @Test
    void testMakePayment_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> paymentService.makePayment(1L, "Credit Card"));

        assertEquals("Order not found", exception.getMessage());
    }
}
