package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer>{

    
}
