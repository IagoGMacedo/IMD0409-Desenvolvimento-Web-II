package com.macedo.Ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.macedo.Ecommerce.exception.InvalidPasswordException;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Transactional
    public User salvar(User usuario) {
        return repository.save(usuario);
    }

    public UserDetails autenticar(User usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getPassword(), user.getPassword());

        if (senhasBatem) {
            return user;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Consulta ao repositório para obter o usuário com base no nome de usuário
        // fornecido
        User usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles =  new String[] { usuario.getRole().toString() } ;

        // Cria e retorna o objeto UserDetails com os detalhes do usuário
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

}
