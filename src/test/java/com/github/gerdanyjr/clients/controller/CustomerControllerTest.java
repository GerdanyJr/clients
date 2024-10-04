package com.github.gerdanyjr.clients.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gerdanyjr.clients.model.Customer;
import com.github.gerdanyjr.clients.service.CustomerService;

@WebMvcTest
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer(
                "First",
                "Last",
                "123456789",
                "test@email.com");
    }

    @DisplayName("Should return registered Customer when a valid Customer is passed")
    @Test
    void givenValidCustomer_whenCreateCustomer_thenReturnCustomer() throws JsonProcessingException, Exception {
        when(customerService
                .createCustomer(any(Customer.class)))
                .thenAnswer(answer -> answer.getArgument(0));

        ResultActions response = mockMvc.perform(post("/customers")
                .content(objectMapper.writeValueAsString(customer))
                .contentType(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName")
                        .value(customer.getFirstName()));
    }
}
