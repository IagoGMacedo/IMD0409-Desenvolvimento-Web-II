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
import com.jeanlima.springmvcapp.service.AlunoService;
import com.jeanlima.springmvcapp.service.CursoService;
import com.jeanlima.springmvcapp.service.MockDataService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    @Qualifier("alunoServiceImpl")
    AlunoService alunoService;

    @Autowired
    @Qualifier("cursoServiceImpl")
    CursoService cursoService;

    @Autowired
    MockDataService mockDataService;


    @RequestMapping("/showForm")
    public String showFormAluno(Model model){

        model.addAttribute("aluno", new Aluno());
        model.addAttribute("cursos", cursoService.getListaCursos());
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
    public ModelAndView getListaAlunosCurso(Model model) {

        for (Curso curso : cursoService.getListaCursos()) {
            //alterar isso aqui depois pra ser id
            curso.setListaAlunos(alunoService.getAlunosByCurso(curso.getNome()));
        }
        model.addAttribute("cursos", cursoService.getListaCursos());
        return new ModelAndView("aluno/listaPorCurso");
    }

    @RequestMapping("/getListaAlunosLinguagem")
    public ModelAndView getListaAlunosLinguagem() {
        ModelAndView model = new ModelAndView("aluno/listaPorLinguagem");

        List<Aluno> alunosJava = alunoService.getAlunosByLinguagem("Java");
        List<Aluno> alunosC = alunoService.getAlunosByLinguagem("C");
        List<Aluno> alunosPython = alunoService.getAlunosByLinguagem("Python");
        List<Aluno> alunosJavascript = alunoService.getAlunosByLinguagem("Javascript");
        
        model.addObject("alunosJava",alunosJava);
        model.addObject("alunosC",alunosC);
        model.addObject("alunosPython",alunosPython);
        model.addObject("alunosJavascript",alunosJavascript);

        return model;
    }

    @RequestMapping("/getListaAlunosSo")
    public ModelAndView getListaAlunosSo() {
        ModelAndView model = new ModelAndView("aluno/listaPorSo");
        List<Aluno> alunosWindows = alunoService.getAlunosBySO("Windows");
        List<Aluno> alunosLinux = alunoService.getAlunosBySO("Linux");
        List<Aluno> alunosOsx = alunoService.getAlunosBySO("OSX");  
        model.addObject("alunosWindows",alunosWindows);
        model.addObject("alunosLinux",alunosLinux);
        model.addObject("alunosOsx",alunosOsx);
        return model;
    }
    
    @RequestMapping("/deleteAluno/{id}")
    public String deleteAluno(@PathVariable("id") Integer id) {
        Aluno deleteAluno = alunoService.getAlunoById(id);
        if(deleteAluno != null)
            alunoService.deletarAluno(deleteAluno);
        return "redirect:/aluno/getListaAlunos";
    }

    @RequestMapping("/detailsAluno/{id}")
    public ModelAndView detailsAluno(@PathVariable("id") Integer id) {
        Aluno deleteAluno = alunoService.getAlunoById(id);
        if(deleteAluno != null){
            System.out.println("achei aluno");
            ModelAndView model = new ModelAndView("aluno/paginaAluno");
            model.addObject("aluno", deleteAluno);
            return model;
        }
        System.out.println("nao achei aluno");
        return new ModelAndView("redirect:/aluno/getListaAlunos");
    }

    

    
    
}
