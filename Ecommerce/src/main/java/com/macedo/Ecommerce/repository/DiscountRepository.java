package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {
    
}
