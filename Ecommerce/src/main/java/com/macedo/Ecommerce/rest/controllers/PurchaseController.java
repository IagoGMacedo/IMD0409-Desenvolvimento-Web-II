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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/purchases")
@RestController
@RequiredArgsConstructor
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Operation(description = "Lista as Purchases existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Purchases conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchases(Purchase filtro) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchases(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca Purchase pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Purchase com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchaseById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.getPurchaseById(id)), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchasesByUserId(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchasesByUserId(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Purchase por DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a Purchase criada"),
            @ApiResponse(responseCode = "404", description = "O usuário atrelado à Purchase não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> createPurchase(@RequestBody @Valid RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.createPurchase(Purchase)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza uma Purchase com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Purchase atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> updatePurchase(@PathVariable Integer id,
            @RequestBody @Valid RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.updatePurchase(id, Purchase)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Purchase com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Purchase atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> patchPurchase(@PathVariable Integer id,
            @RequestBody RegisterPurchaseDTO PurchaseIncompletaDTO) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.patchPurchase(id, PurchaseIncompletaDTO)),
                HttpStatus.OK);
    }

    @Operation(description = "Exclui uma Purchase pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A Purchase foi deletada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Purchase com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok().build();
    }

}
