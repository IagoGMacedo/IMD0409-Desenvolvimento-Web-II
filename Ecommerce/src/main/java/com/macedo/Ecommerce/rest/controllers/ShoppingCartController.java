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

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;
import com.macedo.Ecommerce.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/shoppingCarts")
@RestController
@RequiredArgsConstructor
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(description = "Busca ShoppingCart pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ShoppingCart com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ShoppingCart com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Integer id) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova ShoppingCart por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a ShoppingCart criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à ShoppingCart não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ShoppingCartDTO> save(@RequestBody ShoppingCartDTO ShoppingCart) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.save(ShoppingCart)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma ShoppingCart pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A ShoppingCart foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ShoppingCart com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        shoppingCartService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma ShoppingCart com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ShoppingCart atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ShoppingCart com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ShoppingCartDTO> update(@PathVariable Integer id, @RequestBody ShoppingCartDTO ShoppingCart) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.update(id, ShoppingCart)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma ShoppingCart com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ShoppingCart atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ShoppingCart com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ShoppingCartDTO> patch(@PathVariable Integer id, @RequestBody ShoppingCartDTO ShoppingCartIncompletaDTO) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.patch(id, ShoppingCartIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as ShoppingCarts existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de ShoppingCarts conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> find(ShoppingCart filtro) {
        return new ResponseEntity<List<ShoppingCartDTO>>((shoppingCartService.findAll(filtro)), HttpStatus.OK);
    }
}
