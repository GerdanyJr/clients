package com.github.gerdanyjr.clients.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gerdanyjr.clients.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCpf(String cpf);
}
