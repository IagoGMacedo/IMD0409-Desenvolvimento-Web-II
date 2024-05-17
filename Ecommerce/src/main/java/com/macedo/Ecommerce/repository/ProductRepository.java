package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>{

    public List<Product> findProductsByCategoriesId(Integer categoryId);
}
