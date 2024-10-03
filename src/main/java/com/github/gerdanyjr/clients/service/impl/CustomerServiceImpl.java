package com.github.gerdanyjr.clients.service.impl;

import java.util.List;
import java.util.Optional;

import com.github.gerdanyjr.clients.exception.ConflictException;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Customer findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Customer findByCpf(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCpf'");
    }

    @Override
    public Customer updateCustomer(String id, Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCustomer'");
    }

    @Override
    public void deleteCustomer(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCustomer'");
    }

}
