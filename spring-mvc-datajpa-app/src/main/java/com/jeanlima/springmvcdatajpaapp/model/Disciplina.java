package com.jeanlima.springmvcdatajpaapp.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String nome;

    @Column(length = 50)
    private String ementa;

    @Column(length = 7)
    private String codigo;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
    @JoinTable(name="disciplina_curso",
    joinColumns=@JoinColumn(name="disciplina_id"), 
    inverseJoinColumns=@JoinColumn(name="curso_id"))
    private List<Curso> cursos;

    public Disciplina() {
    }

    public Disciplina(String ementa, String codigo, String nome) {
        this.ementa = ementa;
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getementa() {
        return ementa;
    }

    public void setementa(String ementa) {
        this.ementa = ementa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }


    @Override
    public String toString() {
        return "Disciplina [nome="+nome+",codigo=" + codigo + ", ementa=" + ementa + ", id=" + id
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ementa == null) ? 0 : ementa.hashCode());
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Disciplina other = (Disciplina) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ementa == null) {
            if (other.ementa != null)
                return false;
        } else if (!ementa.equals(other.ementa))
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

   
    
    
    

    
    
}
