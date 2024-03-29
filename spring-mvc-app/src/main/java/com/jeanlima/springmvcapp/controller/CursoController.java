package com.jeanlima.springmvcapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.springmvcapp.model.Aluno;
import com.jeanlima.springmvcapp.model.Curso;
import com.jeanlima.springmvcapp.service.CursoService;

@Controller
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    @Qualifier("cursoServiceImpl")
    CursoService cursoService;

    @RequestMapping("/getListaCursos")
    public String showListaCurso(Model model){
        List<Curso> Cursos = cursoService.getListaCursos();
        model.addAttribute("cursos",Cursos);
        return "Curso/listaCursos";
    }

    @RequestMapping("/showForm")
    public String showFormCurso(Model model){
        model.addAttribute("curso", new Curso());
        return "curso/formCurso";
    }

    @RequestMapping("/addCurso")
    public String showFormCurso(@ModelAttribute("Curso") Curso curso,  Model model){
        if(curso.getId() != null){
            System.out.println("entrou pra atualizar");
            System.out.println("novo nome: "+curso.getNome());
            cursoService.atualizarCurso(curso);
        } else{
            System.out.println("entrou pra salvar");
            cursoService.salvarCurso(curso);
        }
        return "redirect:/curso/getListaCursos";
    }

    

    @RequestMapping("/deleteCurso/{id}")
    public String deleteCurso(@PathVariable("id") Integer id) {
        Curso deleteCurso = cursoService.getCursoById(id);
        if(deleteCurso != null)
            cursoService.deletarCurso(deleteCurso);
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
