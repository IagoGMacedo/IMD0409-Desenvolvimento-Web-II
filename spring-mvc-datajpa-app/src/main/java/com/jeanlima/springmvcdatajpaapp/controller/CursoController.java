package com.jeanlima.springmvcdatajpaapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.springmvcdatajpaapp.model.Curso;
import com.jeanlima.springmvcdatajpaapp.service.CursoService;

@Controller
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    @Qualifier("cursoService")
    CursoService cursoService;

    @RequestMapping("/getListaCursos")
    public String showListaCurso(Model model){
        List<Curso> Cursos = cursoService.getCursos();
        model.addAttribute("cursos",Cursos);
        return "Curso/listaCursos";
    }

    @RequestMapping("/showForm")
    public String showFormCurso(Model model){
        model.addAttribute("curso", new Curso());
        return "curso/formCurso";
    }

    @RequestMapping("/addCurso")
    public String addCurso(@ModelAttribute("Curso") Curso curso,  Model model){
        if(curso.getId() != null){
            cursoService.updateCurso(curso);
        } else{
            cursoService.saveCurso(curso);
        }
        return "redirect:/curso/getListaCursos";
    }

    @RequestMapping("/deleteCurso/{id}")
    public String deleteCurso(@PathVariable("id") Integer id) {
        Curso deleteCurso = cursoService.getCursoById(id);
        if(deleteCurso != null)
            cursoService.deleteCurso(deleteCurso);
        return "redirect:/curso/getListaCursos";
    }

    @RequestMapping("/editCurso/{id}")
    public String editCurso(@PathVariable("id") Integer id, Model model) {
        Curso editCurso = cursoService.getCursoById(id);
        if(editCurso != null){
            model.addAttribute("curso", editCurso);
            return "Curso/formCurso";
        } 
        return "redirect:/curso/getListaCursos";  
    }
}
