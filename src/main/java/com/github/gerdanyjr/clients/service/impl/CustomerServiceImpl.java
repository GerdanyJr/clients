package com.github.gerdanyjr.clients.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.github.gerdanyjr.clients.exception.ConflictException;
import com.github.gerdanyjr.clients.exception.NotFoundException;
import com.github.gerdanyjr.clients.model.Customer;
import com.github.gerdanyjr.clients.repository.CustomerRepository;
import com.github.gerdanyjr.clients.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Optional<Customer> foundByCpf = customerRepository.findByCpf(customer.getCpf());
        foundByCpf.ifPresent((c) -> {
            throw new ConflictException("Customer with cpf: " + customer.getCpf() + " already exists!");
        });

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Customer foundById = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
        return foundById;
    }

    @Override
    public Customer findByCpf(String cpf) {
        Optional<Customer> foundCustomer = customerRepository.findByCpf(cpf);
        return foundCustomer
                .orElseThrow(() -> new NotFoundException("Customer not found with cpf " + cpf));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
        BeanUtils.copyProperties(customer, foundCustomer);
        return customerRepository.save(foundCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
        customerRepository.delete(foundCustomer);
    }

}
