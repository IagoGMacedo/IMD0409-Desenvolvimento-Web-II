package com.jeanlima.springrestapiapp.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jeanlima.springrestapiapp.model.Tarefa;
import com.jeanlima.springrestapiapp.rest.dto.AlteracaoUsuarioDTO;
import com.jeanlima.springrestapiapp.rest.dto.TarefaDTO;
import com.jeanlima.springrestapiapp.service.TarefaService;
import com.jeanlima.springrestapiapp.utils.Patcher;

@RequestMapping("/api/tarefas")
@RestController
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private Patcher patcher;

    @GetMapping("{id}")
    public Tarefa getTarefaById(@PathVariable Integer id) {
        return tarefaService
                .encontrarPeloId(id)
                .orElseThrow(() -> // se nao achar lança o erro!
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa save(@RequestBody TarefaDTO tarefa) {
        return tarefaService.salvar(tarefa);
    }

    

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        tarefaService.encontrarPeloId(id)
                .map(tarefa -> {
                    tarefaService.deletar(tarefa);
                    return tarefa;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"));

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
            @RequestBody Tarefa tarefa) {
        tarefaService
                .encontrarPeloId(id)
                .map(tarefaExistente -> {
                    tarefa.setId(tarefaExistente.getId());
                    tarefaService.salvar(tarefa);
                    return tarefaExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Tarefa tarefaIncompleta) {
        tarefaService
                .encontrarPeloId(id)
                .map(tarefaExistente -> {
                    patcher.patchProperties(tarefaIncompleta, tarefaExistente);
                    tarefaService.salvar(tarefaExistente);
                    return tarefaExistente;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Tarefa não encontrada."));
    }

    @GetMapping
    public List<Tarefa> find(Tarefa filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return tarefaService.encontrarTodos(example);
    }

     
    @PatchMapping("/alterarUsuario")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsuarioTarefa(@RequestBody AlteracaoUsuarioDTO dto){
        tarefaService.atualizarUsuario(dto.getIdTarefa(), dto.getIdUsuario());
    }

    @GetMapping("/consultarTarefas/{id}")
    public List<Tarefa> consultasTarefasUsuario(@PathVariable Integer id){
        return tarefaService.consultarTarefasUsuario(id);
    }

    

    
    

}
