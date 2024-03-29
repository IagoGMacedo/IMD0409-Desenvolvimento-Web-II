package com.jeanlima.springmvcapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeanlima.springmvcapp.model.Aluno;
import com.jeanlima.springmvcapp.model.Curso;

@Service
public interface CursoService {

    public void salvarCurso(Curso curso);
    public Curso getCursoById(Integer id);
    public List<Curso> getListaCursos();
    public void deletarCurso(Curso curso);
    public void atualizarCurso(Curso curso);

    
}
