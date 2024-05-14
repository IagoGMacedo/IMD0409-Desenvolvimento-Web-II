package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address,Integer>{

    
}
