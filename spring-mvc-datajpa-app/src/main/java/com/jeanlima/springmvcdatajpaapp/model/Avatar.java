package com.jeanlima.springmvcdatajpaapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avatar")
public class Avatar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 100)
    private String nomeFantasia;

    @OneToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    public Avatar(){

    }

    public Avatar(String nomeFantasia){
        this.nomeFantasia = nomeFantasia;
    }

    public Integer getId() {
        return id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    @Override
    public String toString() {
        return nomeFantasia;
    }
}
