package com.jeanlima.springrestapiapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.model.Produto;


public interface ProdutoRepository extends JpaRepository<Produto,Integer>{

    @Query(" select p from Produto p left join fetch p.estoque where p.id = :id ")
    Optional<Produto> findByIdFetchItens(@Param("id") Integer id);
}
