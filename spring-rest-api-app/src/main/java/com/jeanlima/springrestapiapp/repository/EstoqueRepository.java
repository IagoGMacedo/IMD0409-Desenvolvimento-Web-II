package com.jeanlima.springrestapiapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapiapp.model.Estoque;


public interface EstoqueRepository extends JpaRepository<Estoque,Integer> {

    @Query(value = " select e from Estoque e where e.produto.descricao like %:nome% ")
    List<Estoque> encontrarPorNome(@Param("nome") String nome);
    
}
