package com.macedo.Ecommerce.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.macedo.Ecommerce.exception.InvalidPasswordException;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.rest.dto.CredentialsDTO;
import com.macedo.Ecommerce.rest.dto.TokenDTO;
import com.macedo.Ecommerce.rest.dto.UserDTO;
import com.macedo.Ecommerce.security.JwtService;
import com.macedo.Ecommerce.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO salvar(@RequestBody @Valid UserDTO usuario) {
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody @Valid CredentialsDTO credenciais) {
        return usuarioService.autenticar(credenciais);
    }
}
