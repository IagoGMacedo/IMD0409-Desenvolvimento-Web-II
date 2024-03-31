package com.jeanlima.springmvcdatajpaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeanlima.springmvcdatajpaapp.model.Disciplina;
import com.jeanlima.springmvcdatajpaapp.service.DisciplinaService;
import com.jeanlima.springmvcdatajpaapp.service.CursoService;




@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {


    @Autowired
    @Qualifier("disciplinaService")
    DisciplinaService disciplinaService;

    @Autowired
    @Qualifier("cursoService")
    CursoService cursoService;
    


    @RequestMapping("/showForm")
    public String showFormDisciplina(Model model){

        Disciplina disciplina = new Disciplina();
        model.addAttribute("disciplina", disciplina);
        model.addAttribute("cursos", cursoService.getCursos());
        return "disciplina/formDisciplina";
    }

    @RequestMapping("/addDisciplina")
    public String addDisciplina(
        @ModelAttribute("disciplina") Disciplina disciplina,
        Model model){

        disciplinaService.salvarDisciplina(disciplina);
        model.addAttribute("disciplina", disciplina);
        return "redirect:/disciplina/getListaDisciplinas";
    }

    @RequestMapping("/getListaDisciplinas")
    public String showListaDisciplina(Model model){
        List<Disciplina> disciplinas = disciplinaService.getDisciplinas();
        model.addAttribute("disciplinas",disciplinas);
        return "disciplina/listaDisciplinas";

    }


    
    
}
