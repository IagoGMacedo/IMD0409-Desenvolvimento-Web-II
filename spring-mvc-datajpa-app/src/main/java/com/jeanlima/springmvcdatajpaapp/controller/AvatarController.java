package com.jeanlima.springmvcdatajpaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeanlima.springmvcdatajpaapp.model.Aluno;
import com.jeanlima.springmvcdatajpaapp.model.Avatar;
import com.jeanlima.springmvcdatajpaapp.model.Curso;
import com.jeanlima.springmvcdatajpaapp.service.AlunoService;
import com.jeanlima.springmvcdatajpaapp.service.AvatarService;




@Controller
@RequestMapping("/avatar")
public class AvatarController {


    @Autowired
    @Qualifier("avatarService")
    AvatarService avatarService;

    @Autowired
    @Qualifier("alunoServiceImpl")
    AlunoService alunoService;


    @RequestMapping("/showForm")
    public String showFormAvatar(Model model){

        Aluno aluno = new Aluno();
        Curso curso = new Curso();
        aluno.setCurso(curso);

        Avatar avatar = new Avatar();
        avatar.setAluno(aluno);

        model.addAttribute("avatar", avatar);
        model.addAttribute("alunos", alunoService.getListaAluno());
        return "avatar/formAvatar";
    }

    @RequestMapping("/addAvatar")
    public String addAvatar(
        @ModelAttribute("avatar") Avatar avatar,  
        Model model){

        //Aluno fetchAluno = alunoService.findByIdFetchCurso(avatar.getAluno().getId());
        //avatar.setAluno(fetchAluno);
        avatarService.salvarAvatar(avatar);

        model.addAttribute("avatar", avatar);
        return "redirect:/avatar/getListaAvatares";
    }

    @RequestMapping("/getListaAvatares")
    public String showListaAvatar(Model model){
        List<Avatar> avatares = avatarService.getListaAvatar();
        

        model.addAttribute("avatares",avatares);
        return "avatar/listaAvatares";

    }


    
    
}
