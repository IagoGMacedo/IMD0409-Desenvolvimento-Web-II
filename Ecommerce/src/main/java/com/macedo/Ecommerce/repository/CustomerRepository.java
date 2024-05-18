package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    Customer findByEmail(String email);
}
