package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

    
}
