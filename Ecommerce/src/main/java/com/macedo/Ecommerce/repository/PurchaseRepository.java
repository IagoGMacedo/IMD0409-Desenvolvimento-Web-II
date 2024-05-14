package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer>{

    
}
