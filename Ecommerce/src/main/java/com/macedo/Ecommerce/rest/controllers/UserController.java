package com.macedo.Ecommerce.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Operation(description = "Registra um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O Usuário foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
            @ApiResponse(responseCode = "409", description = "Já existe um Usuário com esse Login no sistema"),

    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> salvar(@RequestBody @Valid UserDTO usuario) {
        return new ResponseEntity<UserDTO>(usuarioService.salvar(usuario), HttpStatus.CREATED);
    }

    @Operation(description = "Loga um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Login foi bem sucedido"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
            @ApiResponse(responseCode = "401", description = "As credenciais não foram encontradas")
    })
    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid CredentialsDTO credenciais) {
        return new ResponseEntity<TokenDTO>(usuarioService.autenticar(credenciais), HttpStatus.OK);
    }
}
