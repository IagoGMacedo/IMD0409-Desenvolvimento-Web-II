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

import com.macedo.Ecommerce.rest.dto.CategoryDTO;
import com.macedo.Ecommerce.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {
        @Autowired
        private CategoryService categoryService;

        @Operation(description = "Lista as Categorias existentes a partir de filtro, se passado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de Categorias conforme filtro"),
        })
        @GetMapping
        public ResponseEntity<List<CategoryDTO>> getCategories(CategoryDTO filtro) {
                return new ResponseEntity<List<CategoryDTO>>((categoryService.getCategories(filtro)), HttpStatus.OK);
        }

        @Operation(description = "Busca Categoria pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a Categoria com o ID específicado"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Categoria com o ID específicado")
        })
        @GetMapping("{id}")
        public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
                return new ResponseEntity<CategoryDTO>((categoryService.getCategoryById(id)), HttpStatus.OK);
        }

        @Operation(description = "Cria uma nova Categoria")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "A nova Categoria foi criada"),
                        @ApiResponse(responseCode = "400", description = "O campo nome é obrigatório"),
                        @ApiResponse(responseCode = "409", description = "Já existe uma categoria com esse nome")
        })
        @PostMapping
        public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO Category) {
                return new ResponseEntity<CategoryDTO>((categoryService.createCategory(Category)), HttpStatus.CREATED);
        }

        @Operation(description = "Atualiza uma Categoria com o método PUT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "A categoria foi atualizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "O campo nome é obrigatório"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Categoria com o ID específicado"),
                        @ApiResponse(responseCode = "409", description = "Já existe uma categoria com esse nome")
        })
        @PutMapping("{id}")
        public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id,
                        @RequestBody @Valid CategoryDTO Category) {
                return new ResponseEntity<CategoryDTO>((categoryService.updateCategory(id, Category)), HttpStatus.OK);
        }

        @Operation(description = "Atualiza uma Categoria com o método PATCH")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "A categoria foi atualizada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Categoria com o ID específicado"),
                        @ApiResponse(responseCode = "409", description = "Já existe uma categoria com esse nome")
        })
        @PatchMapping("{id}")
        public ResponseEntity<CategoryDTO> patchCategory(@PathVariable Integer id,
                        @RequestBody CategoryDTO CategoryIncompletaDTO) {
                return new ResponseEntity<CategoryDTO>((categoryService.patchCategory(id, CategoryIncompletaDTO)),
                                HttpStatus.OK);
        }

        @Operation(description = "Exclui uma Categoria pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "A Categoria foi deletada"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Categoria com o ID específicado")
        })
        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
                categoryService.deleteCategory(id);
                return ResponseEntity.ok().build();
        }

}
