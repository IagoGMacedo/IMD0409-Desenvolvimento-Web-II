package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem,Integer>{

    
}
