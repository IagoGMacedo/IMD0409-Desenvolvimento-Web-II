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

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.service.UsuarioService;
import com.jeanlima.springrestapiapp.utils.Patcher;

@RequestMapping("/api/usuarios")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Patcher patcher;

    @GetMapping("{id}")
    public Usuario getUsuarioById(@PathVariable Integer id) {
        return usuarioService
                .encontrarPeloId(id)
                .orElseThrow(() -> // se nao achar lança o erro!
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        usuarioService.encontrarPeloId(id)
                .map(usuario -> {
                    usuarioService.deletar(usuario);
                    return usuario;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
            @RequestBody Usuario usuario) {
        usuarioService
                .encontrarPeloId(id)
                .map(usuarioExistente -> {
                    usuario.setId(usuarioExistente.getId());
                    usuarioService.salvar(usuario);
                    return usuarioExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Integer id, @RequestBody Usuario clienteIncompleto) {
        usuarioService
                .encontrarPeloId(id)
                .map(clienteExistente -> {
                    patcher.patchProperties(clienteIncompleto, clienteExistente);
                    usuarioService.salvar(clienteExistente);
                    return clienteExistente;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Usuario não encontrado."));
    }

    @GetMapping
    public List<Usuario> find(Usuario filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return usuarioService.encontrarTodos(example);
    }


    
}
