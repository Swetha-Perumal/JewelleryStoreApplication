package com.jewellery.customer;


import com.jewellery.dto.AddressDTO;
import com.jewellery.dto.CartDTO;
import com.jewellery.dto.CustomerDTO;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.feign.AddressClient;
import com.jewellery.model.Cart;
import com.jewellery.model.Customer;
import com.jewellery.model.User;
import com.jewellery.repository.CustomerRepository;
import com.jewellery.security.JwtService;
import com.jewellery.service.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressClient addressClient;

    @Mock
    private JwtService jwtService;

    @Mock
    private ModelMapper modelMapper;

    private Customer customer;
    private CustomerDTO customerDTO;
    private AddressDTO addressDTO;
    private Cart cart;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserName("testUser");

        cart = new Cart();

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setMobileNumber("1234567890");
        customer.setUser(user);
        customer.setCart(cart);

        customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setMobileNumber("1234567890");

        addressDTO = new AddressDTO();
        addressDTO.setAddressId(100L);
    }

    @Test
    void testGetAddressByCustomerId_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(jwtService.generateToken("testUser")).thenReturn("mockedToken");
        when(addressClient.getAddressById(1L, "Bearer mockedToken")).thenReturn(addressDTO);

        AddressDTO result = customerService.getAddressByCustomerId(1L);

        assertNotNull(result);
        assertEquals(100L, result.getAddressId());

        verify(customerRepository, times(1)).findById(1L);
        verify(jwtService, times(1)).generateToken("testUser");
        verify(addressClient, times(1)).getAddressById(1L, "Bearer mockedToken");
    }

    @Test
    void testGetAddressByCustomerId_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> customerService.getAddressByCustomerId(1L));
        assertEquals("User not found", exception.getMessage());

        verify(customerRepository, times(1)).findById(1L);
        verify(jwtService, never()).generateToken(anyString());
        verify(addressClient, never()).getAddressById(anyLong(), anyString());
    }

    @Test
    void testUpdateCustomer_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO updatedCustomer = customerService.updateCustomer(customerDTO);

        assertNotNull(updatedCustomer);
        assertEquals("John", updatedCustomer.getFirstName());

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(modelMapper, times(1)).map(customer, CustomerDTO.class);
    }

    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());

        verify(customerRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(customer, CustomerDTO.class);
    }

    @Test
    void testGetCartByCustomerId_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(new CartDTO());

        CartDTO cartDTO = customerService.getCartByCustomerId(1L);

        assertNotNull(cartDTO);

        verify(customerRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(cart, CartDTO.class);
    }
}

