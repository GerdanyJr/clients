package com.github.gerdanyjr.clients.controller;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.github.gerdanyjr.clients.exception.ConflictException;
import com.github.gerdanyjr.clients.exception.NotFoundException;
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
                                1L,
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

        @DisplayName("Should return a 409 status code when invalid customer is passed")
        @Test
        void givenExistingCustomer_whenCreateCustomer_thenReturn409() throws JsonProcessingException, Exception {
                when(customerService
                                .createCustomer(any(Customer.class)))
                                .thenThrow(ConflictException.class);

                ResultActions response = mockMvc.perform(post("/customers")
                                .content(objectMapper.writeValueAsString(customer))
                                .contentType(MediaType.APPLICATION_JSON));

                response.andExpect(status().is(409));
        }

        @DisplayName("Should return Customer list when findAll")
        @Test
        void whenFindAll_thenReturnCustomers() throws Exception {
                List<Customer> customers = List.of(customer, customer);
                when(customerService.findAll())
                                .thenReturn(customers);

                ResultActions response = mockMvc
                                .perform(get("/customers")
                                                .contentType(MediaType.APPLICATION_JSON));

                response
                                .andExpect(status().isOk())
                                .andExpect(content()
                                                .json(objectMapper.writeValueAsString(customers)));
        }

        @DisplayName("Should return Customer when a valid id is passed")
        @Test
        void givenValidId_whenFindById_thenReturnCustomer() throws Exception {
                when(customerService.findById(anyLong()))
                                .thenReturn(customer);

                ResultActions response = mockMvc
                                .perform(get("/customers/" + customer.getId())
                                                .contentType(MediaType.APPLICATION_JSON));

                response
                                .andExpect(jsonPath("$.id").value(customer.getId()))
                                .andExpect(status().isOk())
                                .andExpect(content()
                                                .json(objectMapper.writeValueAsString(customer)));
        }

        @DisplayName("Should return 404 when a invalid customer id is passed")
        @Test
        void givenInvalidId_whenFindById_thenReturn404() throws Exception {
                when(customerService.findById(anyLong()))
                                .thenThrow(NotFoundException.class);

                ResultActions response = mockMvc
                                .perform(get("/customers/" + customer.getId())
                                                .contentType(MediaType.APPLICATION_JSON));

                response.andExpect(status().isNotFound());
        }
}
