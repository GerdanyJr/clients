package com.github.gerdanyjr.clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gerdanyjr.clients.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
