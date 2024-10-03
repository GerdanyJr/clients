package com.github.gerdanyjr.clients.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.gerdanyjr.clients.model.Customer;

@Service
public interface CustomerService {
    Customer createCustomer(Customer customer);

    List<Customer> findAll();

    Customer findById(Long id);

    Customer findByCpf(String cpf);

    Customer updateCustomer(String id, Customer customer);

    void deleteCustomer(String id);
}
