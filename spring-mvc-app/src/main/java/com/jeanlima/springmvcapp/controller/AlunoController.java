package com.jeanlima.springmvcapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.springmvcapp.model.Aluno;
import com.jeanlima.springmvcapp.service.AlunoService;
import com.jeanlima.springmvcapp.service.MockDataService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    @Qualifier("alunoServiceImpl")
    AlunoService alunoService;

    @Autowired
    MockDataService mockDataService;


    @RequestMapping("/showForm")
    public String showFormAluno(Model model){

        model.addAttribute("aluno", new Aluno());
        model.addAttribute("cursos", mockDataService.getCursos());
        model.addAttribute("sistemasOperacionais", mockDataService.getSistemasOperacionais());
        return "aluno/formAluno";
    }

    @RequestMapping("/addAluno")
    public String showFormAluno(@ModelAttribute("aluno") Aluno aluno,  Model model){

        alunoService.salvarAluno(aluno);
        model.addAttribute("aluno", aluno);
        return "aluno/paginaAluno";
    }

    @RequestMapping("/getListaAlunos")
    public String showListaAluno(Model model){

        List<Aluno> alunos = alunoService.getListaAluno();
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";

    }

    @RequestMapping("/getListaAlunosCurso")
    public String getListaAlunosCurso(@RequestParam String curso, Model model) {
        List<Aluno> alunos = alunoService.getAlunosByCurso(curso);
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";
    }

    @RequestMapping("/getListaAlunosLinguagem")
    public String getListaAlunosLinguagem(@RequestParam String linguagem, Model model) {
        List<Aluno> alunos = alunoService.getAlunosByLinguagem(linguagem);
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";
    }

    @RequestMapping("/getListaAlunosSO")
    public String getListaAlunosSo(@RequestParam String So, Model model) {
        List<Aluno> alunos = alunoService.getAlunosBySO(So);
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";
    }
    

    
    
}
