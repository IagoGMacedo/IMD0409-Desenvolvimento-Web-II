package com.macedo.Ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    List<CreditCard> findByCustomerId(Integer customerId);

}
