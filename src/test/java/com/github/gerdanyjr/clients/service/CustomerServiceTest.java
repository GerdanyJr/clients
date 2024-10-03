package com.github.gerdanyjr.clients.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.gerdanyjr.clients.exception.ConflictException;
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

    @DisplayName("Should return a created customer when a valid Customer is passed")
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

    @DisplayName("Should throw a exception when a registered cpf is passed")
    @Test
    void givenExistingCpf_whenCreateCustomer_thenThrowConflictException() {
        // given
        when(customerRepository
                .findByCpf(customer.getCpf()))
                .thenReturn(Optional.of(customer));

        // when & then
        assertThrows(ConflictException.class, () -> {
            customerService.createCustomer(customer);
        });
    }

}
