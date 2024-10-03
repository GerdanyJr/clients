package com.github.gerdanyjr.clients.service;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.gerdanyjr.clients.exception.ConflictException;
import com.github.gerdanyjr.clients.exception.NotFoundException;
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

    @DisplayName("Should return a Customer List when findAll")
    @Test
    void whenFindAll_thenReturnCustomerList() {
        // given
        when(customerRepository
                .findAll())
                .thenReturn(List.of(customer, customer));

        // when
        List<Customer> customers = customerService.findAll();

        // then
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    @DisplayName("Should return Customer when a valid id is passed to findById")
    @Test
    void givenValidId_whenFindById_thenReturnCustomerWithId() {
        // given
        customer.setId(1L);
        when(customerRepository
                .findById(1L))
                .thenReturn(Optional.of(customer));

        // when
        Customer foundCustomer = customerService.findById(1L);

        // then
        assertNotNull(foundCustomer);
        assertEquals(1L, foundCustomer.getId());
    }

    @DisplayName("Should throw NotFoundException when a invalid id is passed to findById")
    @Test
    void givenInvalidId_whenFindById_thenThrowException() {
        // given
        when(customerRepository
                .findById(1L))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> {
            customerService.findById(1L);
        });
    }

    @DisplayName("Should return a customer when a valid cpf is passed")
    @Test
    void givenValidCpf_whenFindByCpf_thenReturnCustomer() {
        // given
        when(customerRepository.findByCpf(anyString()))
                .thenReturn(Optional.of(customer));

        // when
        Customer customer = customerService.findByCpf("12345678");

        // then
        assertNotNull(customer);
    }

    @DisplayName("Should throw a exception when a invalid cpf is passed")
    @Test
    void givenInvalidCpf_whenFindByCpf_thenThrowException() {
        // given
        String cpf = "12345678";
        when(customerRepository.findByCpf(cpf))
                .thenReturn(Optional.empty());

        // when & then
        Exception e = assertThrows(NotFoundException.class, () -> {
            customerService
                    .findByCpf("12345678");
        });

        // then
        assertEquals("Customer not found with cpf " + cpf, e.getMessage());
    }

    @DisplayName("should update customer when a valid customer is passed")
    @Test
    void givenValidCustomer_whenUpdate_thenUpdateCustomer() {
        // given
        String cpf = "12345321";
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));
        customer.setCpf(cpf);
        when(customerRepository.save(customer))
                .thenReturn(customer);

        // when
        Customer updated = customerService.updateCustomer(1L, customer);
        // then
        assertEquals(cpf, updated.getCpf());
    }

    @DisplayName("Should throw a exception when a invalid id is passed to update")
    @Test
    void givenInvalidId_whenUpdate_thenThrowException() {
        // given
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when & then
        Exception e = assertThrows(NotFoundException.class, () -> {
            customerService.updateCustomer(1L, any(Customer.class));
        });

        // then
        assertEquals("Customer not found with id: " + 1L, e.getMessage());

    }
}
