package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.ResponsePurchaseDTO;
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

import com.macedo.Ecommerce.model.Purchase;
import com.macedo.Ecommerce.rest.dto.RegisterPurchaseDTO;
import com.macedo.Ecommerce.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/purchases")
@RestController
@RequiredArgsConstructor
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Operation(description = "Busca Purchase pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Purchase com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchaseById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Purchase por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a Purchase criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Purchase não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> save(@RequestBody RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.save(Purchase)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Purchase pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A Purchase foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        purchaseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma Purchase com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Purchase atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> update(@PathVariable Integer id, @RequestBody RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.update(id, Purchase)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Purchase com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Purchase atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> patch(@PathVariable Integer id, @RequestBody RegisterPurchaseDTO PurchaseIncompletaDTO) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.patch(id, PurchaseIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as Purchases existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de Purchases conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> find(Purchase filtro) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.findAll(filtro)), HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ResponsePurchaseDTO>> findByUser(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.findByUser(id)), HttpStatus.OK);
    }
}
