package com.jeanlima.springmvcapp.model;

import java.util.List;


public class Curso {

    private Integer id;
    private String nome;

    private List<Aluno> listaAlunos;
    public Curso(){}

    Curso(Integer id, String nome, List<Aluno> listaAlunos){
        this.id = id;
        this.nome = nome;
        this.listaAlunos = listaAlunos;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(List<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }
    
    
}
