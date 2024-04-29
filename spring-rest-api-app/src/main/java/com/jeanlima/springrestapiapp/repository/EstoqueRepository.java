package com.jeanlima.springrestapiapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapiapp.model.Estoque;


public interface EstoqueRepository extends JpaRepository<Estoque,Integer> {

    

    
    
}
