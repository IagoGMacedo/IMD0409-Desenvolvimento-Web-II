package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer>{

    public Optional<List<Purchase>> findPurchasesByCustomerId(Integer userId);
    
}
