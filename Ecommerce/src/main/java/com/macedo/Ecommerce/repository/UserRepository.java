package com.macedo.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Ecommerce.model.User;


public interface UserRepository extends JpaRepository<User,Integer>{

    User findByEmail(String email);
}
