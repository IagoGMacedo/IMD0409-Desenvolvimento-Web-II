package com.jeanlima.springmvcapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jeanlima.springmvcapp.model.Aluno;

@Component
public class AlunoServiceImpl implements  AlunoService{

    public List<Aluno> alunos = new ArrayList<Aluno>();    

    @Override
    public void salvarAluno(Aluno aluno) {
        System.out.println(aluno.toString());
        try{
            this.alunos.add(aluno);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
        
        
    }

    @Override
    public void deletarAluno(Aluno aluno) {
       this.alunos.remove(aluno);
    }

    @Override
    public Aluno getAlunoById(Integer id) {
        for (Aluno aluno : alunos) {
            if(aluno.getId() == id){
                return aluno;
            }
        }
        return null;
    }

    @Override
    public List<Aluno> getListaAluno() {
        return this.alunos;
    }

    @Override
    public List<Aluno> getAlunosByCurso(String curso) {
        List<Aluno> alunosCurso = new ArrayList<>();
        this.alunos.forEach(
            aluno -> {if(aluno.getCurso().equals(curso)){
                alunosCurso.add(aluno);
            }}
        );
        return alunosCurso;
    }

    @Override
    public List<Aluno> getAlunosByLinguagem(String linguagem) {
        List<Aluno> alunosLinguagem = new ArrayList<>();
        this.alunos.forEach(
            aluno -> {if(aluno.getLinguagem().equals(linguagem)){
                alunosLinguagem.add(aluno);
            }}
        );
        return alunosLinguagem;
    }

    @Override
    public List<Aluno> getAlunosBySO(String SO) {
        List<Aluno> alunosSO = new ArrayList<>();
        this.alunos.forEach(
            aluno -> {if(aluno.getSistemasOperacionas().contains(SO)){
                alunosSO.add(aluno);
            }}
        );
        return alunosSO;
    }

    
}
