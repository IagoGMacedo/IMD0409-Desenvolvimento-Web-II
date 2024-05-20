package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.DiscountDTO;
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

import com.macedo.Ecommerce.model.Discount;
import com.macedo.Ecommerce.service.DiscountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/discounts")
@RestController
@RequiredArgsConstructor
public class DiscountController {
        @Autowired
        private DiscountService discountService;

        @Operation(description = "Lista as Discounts existentes a partir de filtro, se passado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de Discounts conforme filtro"),
        })
        @GetMapping
        public ResponseEntity<List<DiscountDTO>> getDiscounts(Discount filtro) {
                return new ResponseEntity<List<DiscountDTO>>((discountService.getDiscounts(filtro)),
                                HttpStatus.OK);
        }

        @Operation(description = "Busca Discount pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a Discount com o ID específicado"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Discount com o ID específicado")
        })
        @GetMapping("{id}")
        public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable Integer id) {
                return new ResponseEntity<DiscountDTO>((discountService.getDiscountById(id)), HttpStatus.OK);
        }

        @Operation(description = "Adiciona uma nova Discount por DTO")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Retorna a Discount criada"),
                        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Discount não foi encontrado")
        })
        @PostMapping
        public ResponseEntity<DiscountDTO> createDiscount(@RequestBody @Valid DiscountDTO Discount) {
                return new ResponseEntity<DiscountDTO>((discountService.createDiscount(Discount)),
                                HttpStatus.CREATED);
        }

        @Operation(description = "Atualiza uma Discount com o método PUT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a Discount atualizada"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Discount com o ID específicado")
        })
        @PutMapping("{id}")
        public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable Integer id,
                        @RequestBody @Valid DiscountDTO Discount) {
                return new ResponseEntity<DiscountDTO>((discountService.updateDiscount(id, Discount)),
                                HttpStatus.OK);
        }

        @Operation(description = "Atualiza uma Discount com o método PATCH")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a Discount atualizada"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Discount com o ID específicado")
        })
        @PatchMapping("{id}")
        public ResponseEntity<DiscountDTO> patchDiscount(@PathVariable Integer id,
                        @RequestBody DiscountDTO DiscountIncompletaDTO) {
                return new ResponseEntity<DiscountDTO>(
                                (discountService.patchDiscount(id, DiscountIncompletaDTO)),
                                HttpStatus.OK);
        }

        @Operation(description = "Exclui uma Discount pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "A Discount foi deletada"),
                        @ApiResponse(responseCode = "404", description = "Não existe uma Discount com o ID específicado")
        })
        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
                discountService.deleteDiscount(id);
                return ResponseEntity.ok().build();
        }

}
