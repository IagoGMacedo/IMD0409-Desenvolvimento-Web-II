package com.macedo.Ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer>{
}
