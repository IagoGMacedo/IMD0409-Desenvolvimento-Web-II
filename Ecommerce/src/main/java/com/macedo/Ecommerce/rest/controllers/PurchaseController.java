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

    @Operation(description = "Lista as Compras existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Purchases conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchases() {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchases()), HttpStatus.OK);
    }

    @Operation(description = "Busca Compra pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Compra com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Compra com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchaseById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.getPurchaseById(id)), HttpStatus.OK);
    }

    @Operation(description = "Busca as Compras do cliente solicitado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as Compras do Cliente solicitado"),
            @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
    })
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchasesByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchasesByCustomerId(id)),
                HttpStatus.OK);
    }

    @Operation(description = "Realiza uma nova Compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A Compra foi feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Um campo obrigatório não foi preenchido"),
            @ApiResponse(responseCode = "400", description = "Não existe no estoque a quantidade solicitada para um dos Produtos"),
            @ApiResponse(responseCode = "404", description = "O Cliente atrelado à Compra não foi encontrado"),
            @ApiResponse(responseCode = "404", description = "O Endereço atrelado à Compra não foi encontrado"),
            @ApiResponse(responseCode = "404", description = "O Cartão atrelado à Compra não foi encontrado"),
            @ApiResponse(responseCode = "404", description = "Um Produto atrelado à Compra não foi encontrado")

    })
    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> createPurchase(@RequestBody @Valid RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.createPurchase(Purchase)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Compra pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A Compra foi deletada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Compra com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok().build();
    }

}
