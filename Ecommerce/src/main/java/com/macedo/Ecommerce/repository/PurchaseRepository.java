package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Purchase;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer>{

    List<Purchase> findByCustomerId(Integer customerId);
    
}
