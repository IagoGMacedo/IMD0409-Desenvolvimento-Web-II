package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.rest.dto.UserDTO;
import com.macedo.Ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(description = "Lista as Users existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Users conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(User filtro) {
        return new ResponseEntity<List<UserDTO>>((userService.getUsers(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca User pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a User com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe uma User com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<UserDTO>((userService.getUserById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova User por DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a User criada"),
            @ApiResponse(responseCode = "404", description = "O usuário atrelado à User não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO User) {
        return new ResponseEntity<UserDTO>((userService.createUser(User)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza uma User com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a User atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma User com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO User) {
        return new ResponseEntity<UserDTO>((userService.updateUser(id, User)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma User com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a User atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma User com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Integer id, @RequestBody UserDTO UserIncompletaDTO) {
        return new ResponseEntity<UserDTO>((userService.patchUser(id, UserIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Exclui uma User pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A User foi deletada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma User com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
