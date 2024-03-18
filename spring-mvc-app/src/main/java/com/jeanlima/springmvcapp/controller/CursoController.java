package com.jeanlima.springmvcapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.springmvcapp.model.Curso;
import com.jeanlima.springmvcapp.service.CursoService;

@Controller
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    @Qualifier("cursoServiceImpl")
    CursoService cursoService;

    @RequestMapping("/showForm")
    public String showFormCurso(Model model){
        model.addAttribute("curso", new Curso());
        return "curso/formCurso";
    }

    @RequestMapping("/addCurso")
    public String showFormCurso(@ModelAttribute("Curso") Curso Curso,  Model model){
        cursoService.salvarCurso(Curso);
        return "redirect:/curso/getListaCursos";
    }

    @RequestMapping("/getListaCursos")
    public String showListaCurso(Model model){

        List<Curso> Cursos = cursoService.getListaCursos();
        model.addAttribute("cursos",Cursos);
        return "Curso/listaCursos";

    }
}
