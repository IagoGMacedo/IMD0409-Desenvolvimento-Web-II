package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/shoppingCarts")
@RestController
@RequiredArgsConstructor
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(description = "Lista os carrinhos de compra a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de carrinhos de compra conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> getShoppingCarts() {
        return new ResponseEntity<List<ShoppingCartDTO>>((shoppingCartService.getShoppingCarts()), HttpStatus.OK);
    }

    @Operation(description = "Busca carrinho de compras pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o carrinho de compras com o ID especificado"),
            @ApiResponse(responseCode = "404", description = "Não existe um carrinho de compras com o ID especificado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Integer id) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.getShoppingCartById(id)), HttpStatus.OK);
    }

    @Operation(description = "Retorna o carrinho de compras do cliente solicitado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o carrinho de compras do cliente solicitado"),
            @ApiResponse(responseCode = "404", description = "Não existe um cliente com o ID especificado")
    })
    @GetMapping("/customer/{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.getShoppingCartByCustomerId(id)), HttpStatus.OK);
    }

    @Operation(summary = "Adiciona um item ao carrinho de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O item foi adicionado ao carrinho de compras com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um carrinho de compras com o ID especificado"),

    })
    @PostMapping()
    public ResponseEntity<ShoppingCartDTO> addItemToCart(@RequestBody @Valid AddItemShoppingCartDTO addProductItem) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.addItemToCart(addProductItem)),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza a quantidade de um item no carrinho de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A quantidade do item foi atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um carrinho de compras com o ID especificado")
    })
    @PatchMapping()
    public ResponseEntity<ShoppingCartDTO> updateItemQuantity(@RequestBody @Valid RegisterItemShoppingCartDTO dto) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.updateItemQuantity(dto)), HttpStatus.OK);
    }

    @Operation(description = "Exclui um item de um carrinho de compras pelo ID do item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O item foi deletado do carrinho de compras"),
            @ApiResponse(responseCode = "404", description = "Não existe um carrinho de compras com o ID especificado"),
            @ApiResponse(responseCode = "404", description = "Não existe um item o ID especificado")
    })
    @DeleteMapping()
    public ResponseEntity<Void> deleteItemFromCart(@RequestBody @Valid RegisterItemShoppingCartDTO deleteProductItem) {
        shoppingCartService.deleteItemFromCart(deleteProductItem);
        return ResponseEntity.ok().build();
    }

}
