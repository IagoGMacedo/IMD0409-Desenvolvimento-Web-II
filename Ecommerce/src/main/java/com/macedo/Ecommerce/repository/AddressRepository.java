package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.macedo.Ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address,Integer>{

    List<Address> findByCustomerId(Integer customerId);    
}
