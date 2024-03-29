package com.jeanlima.springmvcapp.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.jeanlima.springmvcapp.model.Aluno;
import com.jeanlima.springmvcapp.model.Curso;

@Component
public class CursoServiceImpl implements CursoService {

    public List<Curso> cursos = new ArrayList<Curso>();    


    @Override
    public void salvarCurso(Curso curso) {
        curso.setId(this.cursos.size());

        System.out.println(curso.toString());
        try{
            this.cursos.add(curso);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    @Override
    public Curso getCursoById(Integer id) {
        for (Curso curso : cursos) {
            if(curso.getId() == id){
                return curso;
            }
        }
        return null;
    }

    @Override
    public List<Curso> getListaCursos() {
        return this.cursos;
    }

    @Override
    public void deletarCurso(Curso curso) {
        this.cursos.remove(curso);
        System.out.println("consegui deletar");
    }

    @Override
    public void atualizarCurso(Curso curso) {
        if(curso.getId() != null){
            this.cursos.set(curso.getId(), curso);
        }
    }
    
}
