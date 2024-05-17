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

import com.macedo.Ecommerce.model.Product;
import com.macedo.Ecommerce.rest.dto.ProductDTO;
import com.macedo.Ecommerce.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(description = "Busca Product pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Product com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Product com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return new ResponseEntity<ProductDTO>((productService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Product por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a Product criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Product não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.save(Product)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Product pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A Product foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Product com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma Product com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Product atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Product com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.update(id, Product)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Product com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Product atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Product com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ProductDTO> patch(@PathVariable Integer id, @RequestBody ProductDTO ProductIncompletaDTO) {
        return new ResponseEntity<ProductDTO>((productService.patch(id, ProductIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as Products existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de Products conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> find(Product filtro) {
        return new ResponseEntity<List<ProductDTO>>((productService.findAll(filtro)), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable Integer id) {
        return new ResponseEntity<List<ProductDTO>>((productService.findByCategory(id)), HttpStatus.OK);
    }
}
