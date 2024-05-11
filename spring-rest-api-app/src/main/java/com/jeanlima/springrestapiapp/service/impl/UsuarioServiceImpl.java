package com.jeanlima.springrestapiapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapiapp.model.Usuario;
import com.jeanlima.springrestapiapp.repository.UsuarioRepository;
import com.jeanlima.springrestapiapp.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    @Override
    public Optional<Usuario> encontrarPeloId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void deletar(Usuario usuario) {
        repository.delete(usuario);
    }

    @Override
    public List<Usuario> encontrarTodos(Example example) {
        return repository.findAll(example);
    }
    
}
