package com.github.gerdanyjr.clients.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.gerdanyjr.clients.model.Customer;
import com.github.gerdanyjr.clients.repository.CustomerRepository;
import com.github.gerdanyjr.clients.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer(
                "First",
                "Last",
                "123456789",
                "test@email.com");
    }

    @DisplayName("Should return a created customer when a valid Dto is passed")
    @Test
    void givenValidDto_whenCreateCustomer_thenReturnCustomer() {
        // given
        when(customerRepository.save(customer))
                .thenReturn(customer);

        // when
        Customer newCustomer = customerService.createCustomer(customer);

        // then
        assertNotNull(newCustomer);
        assertEquals(newCustomer.getEmail(), newCustomer.getEmail());
    }

}
