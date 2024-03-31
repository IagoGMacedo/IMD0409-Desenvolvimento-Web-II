package com.jeanlima.springmvcdatajpaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeanlima.springmvcdatajpaapp.model.Aluno;
import com.jeanlima.springmvcdatajpaapp.model.Curso;
import com.jeanlima.springmvcdatajpaapp.service.AlunoService;
import com.jeanlima.springmvcdatajpaapp.service.CursoService;
import com.jeanlima.springmvcdatajpaapp.service.DisciplinaService;




@Controller
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    @Qualifier("alunoServiceImpl")
    AlunoService alunoService;

    @Autowired
    CursoService cursoService;

    @Autowired
    DisciplinaService disciplinaService;


    @RequestMapping("/showForm")
    public String showFormAluno(Model model){

        Aluno aluno = new Aluno();
        Curso curso = new Curso();
        aluno.setCurso(curso);

        model.addAttribute("aluno", aluno);
        model.addAttribute("cursos", cursoService.getCursos());
        model.addAttribute("disciplinas", disciplinaService.getDisciplinas());
        return "aluno/formAluno";
    }

    @RequestMapping("/addAluno")
    public String showFormAluno(
        @ModelAttribute("aluno") Aluno aluno,  
        Model model){

        alunoService.salvarAluno(aluno);

        model.addAttribute("aluno", aluno);
        return "redirect:/aluno/getListaAlunos";
    }

    @RequestMapping("/getListaAlunos")
    public String showListaAluno(Model model){

        List<Aluno> alunos = alunoService.getListaAluno();
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";

    }

    @RequestMapping("/deleteAluno/{id}")
    public String deleteAluno(@PathVariable("id") Integer id) {
        Aluno deleteAluno = alunoService.getAlunoById(id);
        if(deleteAluno != null)
            alunoService.deletarAluno(deleteAluno);
        return "redirect:/aluno/getListaAlunos";
    }

    
    
}
