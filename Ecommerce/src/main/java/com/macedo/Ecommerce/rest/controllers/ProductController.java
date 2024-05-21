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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(description = "Lista os Produtos existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Produtos conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(ProductDTO filtro) {
        return new ResponseEntity<List<ProductDTO>>((productService.getProducts(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca Produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o Produto com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe um Produto com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return new ResponseEntity<ProductDTO>((productService.getProductById(id)), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Integer id) {
        return new ResponseEntity<List<ProductDTO>>((productService.getProductsByCategoryId(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O novo Produto foi criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Cateogira com o ID específicado")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.createProduct(Product)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza um Produto com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Produto foi atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
            @ApiResponse(responseCode = "404", description = "Não existe um Produto com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody @Valid ProductDTO Product) {
        return new ResponseEntity<ProductDTO>((productService.updateProduct(id, Product)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um Produto com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Produto foi atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um Produto com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Integer id,
            @RequestBody ProductDTO ProductIncompletaDTO) {
        return new ResponseEntity<ProductDTO>((productService.patchProduct(id, ProductIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Exclui um Produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Produto foi deletado"),
            @ApiResponse(responseCode = "404", description = "Não existe um Produto com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
