package com.macedo.Ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.ShippingTax;

public interface ShippingTaxRepository extends JpaRepository<ShippingTax,Integer>{

    Optional<ShippingTax> findByState(String state);
    
}
