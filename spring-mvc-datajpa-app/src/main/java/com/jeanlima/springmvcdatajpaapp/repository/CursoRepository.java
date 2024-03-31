package com.jeanlima.springmvcdatajpaapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springmvcdatajpaapp.model.Aluno;
import com.jeanlima.springmvcdatajpaapp.model.Curso;



public interface CursoRepository extends JpaRepository<Curso,Integer>{
    @Query(value = " select c from Curso c")
    List<Curso> retornarCursos();
}
