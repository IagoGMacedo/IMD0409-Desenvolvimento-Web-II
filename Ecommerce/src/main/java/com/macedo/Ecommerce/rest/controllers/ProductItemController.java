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

import com.macedo.Ecommerce.model.ProductItem;
import com.macedo.Ecommerce.rest.dto.ProductItemDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/productItems")
@RestController
@RequiredArgsConstructor
public class ProductItemController {
    @Autowired
    private com.macedo.Ecommerce.service.ProductItemService ProductItemService;

    @Operation(description = "Busca ProductItem pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ProductItem com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ProductItem com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProductItemDTO> getProductItemById(@PathVariable Integer id) {
        return new ResponseEntity<ProductItemDTO>((ProductItemService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova ProductItem por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a ProductItem criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à ProductItem não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ProductItemDTO> save(@RequestBody ProductItemDTO ProductItem) {
        return new ResponseEntity<ProductItemDTO>((ProductItemService.save(ProductItem)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma ProductItem pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A ProductItem foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ProductItem com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ProductItemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma ProductItem com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ProductItem atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ProductItem com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ProductItemDTO> update(@PathVariable Integer id, @RequestBody ProductItemDTO ProductItem) {
        return new ResponseEntity<ProductItemDTO>((ProductItemService.update(id, ProductItem)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma ProductItem com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a ProductItem atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma ProductItem com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ProductItemDTO> patch(@PathVariable Integer id, @RequestBody ProductItemDTO ProductItemIncompletaDTO) {
        return new ResponseEntity<ProductItemDTO>((ProductItemService.patch(id, ProductItemIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as ProductItems existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de ProductItems conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> find(ProductItem filtro) {
        return new ResponseEntity<List<ProductItemDTO>>((ProductItemService.findAll(filtro)), HttpStatus.OK);
    }
}
