package com.macedo.Ecommerce.repository;

import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    
}
