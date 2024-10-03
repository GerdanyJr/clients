package com.github.gerdanyjr.clients.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.gerdanyjr.clients.model.Customer;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer(
                "First",
                "Last",
                "123456789",
                "test@email.com");
    }

    @DisplayName("Should return customers when findAll is called")
    @Test
    void whenFindAll_thenReturnCustomer() {
        // given
        customerRepository.save(customer);

        // when
        List<Customer> foundCustomers = customerRepository.findAll();

        // then
        assertNotNull(foundCustomers, () -> "Customers should not be empty!");
        assertEquals(1, foundCustomers.size());
    }

    @DisplayName("Should return customer when findById with a registered id is passed")
    @Test
    void givenValidId_whenFindById_thenReturnCustomer() {
        // given
        customer.setId(1L);
        customerRepository.save(customer);

        // when
        Optional<Customer> foundCustomer = customerRepository.findById(1L);

        // then
        assertTrue(foundCustomer.isPresent());
        assertEquals(foundCustomer
                .get()
                .getId(),
                1L);
    }
}
