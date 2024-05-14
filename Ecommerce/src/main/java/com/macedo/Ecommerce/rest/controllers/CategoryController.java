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

import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.rest.dto.CategoryDTO;
import com.macedo.Ecommerce.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(description = "Busca Category pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Category com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Category com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        return new ResponseEntity<CategoryDTO>((categoryService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Category por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a Category criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Category não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO Category) {
        return new ResponseEntity<CategoryDTO>((categoryService.save(Category)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Category pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A Category foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Category com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma Category com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Category atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Category com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @RequestBody CategoryDTO Category) {
        return new ResponseEntity<CategoryDTO>((categoryService.update(id, Category)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Category com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Category atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Category com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<CategoryDTO> patch(@PathVariable Integer id, @RequestBody CategoryDTO CategoryIncompletaDTO) {
        return new ResponseEntity<CategoryDTO>((categoryService.patch(id, CategoryIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as Categorys existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de Categorys conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> find(Category filtro) {
        return new ResponseEntity<List<CategoryDTO>>((categoryService.findAll(filtro)), HttpStatus.OK);
    }
    
}
