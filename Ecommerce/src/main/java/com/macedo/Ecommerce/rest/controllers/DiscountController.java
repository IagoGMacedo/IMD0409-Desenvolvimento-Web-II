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

        @Operation(description = "Lista os Descontos existentes a partir de filtro, se passado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de Descontos conforme filtro"),
        })
        @GetMapping
        public ResponseEntity<List<DiscountDTO>> getDiscounts(DiscountDTO filtro) {
                return new ResponseEntity<List<DiscountDTO>>((discountService.getDiscounts(filtro)),
                                HttpStatus.OK);
        }

        @Operation(description = "Busca Desconto pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o Desconto com o ID específicado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Desconto com o ID específicado")
        })
        @GetMapping("{id}")
        public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable Integer id) {
                return new ResponseEntity<DiscountDTO>((discountService.getDiscountById(id)), HttpStatus.OK);
        }

        @Operation(description = "Adiciona um novo Desconto")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "O novo Desconto foi criado"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
        })
        @PostMapping
        public ResponseEntity<DiscountDTO> createDiscount(@RequestBody @Valid DiscountDTO Discount) {
                return new ResponseEntity<DiscountDTO>((discountService.createDiscount(Discount)),
                                HttpStatus.CREATED);
        }

        @Operation(description = "Atualiza um Desconto com o método PUT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Desconto foi atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Desconto com o ID específicado")
        })
        @PutMapping("{id}")
        public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable Integer id,
                        @RequestBody @Valid DiscountDTO Discount) {
                return new ResponseEntity<DiscountDTO>((discountService.updateDiscount(id, Discount)),
                                HttpStatus.OK);
        }

        @Operation(description = "Atualiza um Desconto com o método PATCH")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Desconto foi atualizado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Desconto com o ID específicado")
        })
        @PatchMapping("{id}")
        public ResponseEntity<DiscountDTO> patchDiscount(@PathVariable Integer id,
                        @RequestBody DiscountDTO DiscountIncompletaDTO) {
                return new ResponseEntity<DiscountDTO>(
                                (discountService.patchDiscount(id, DiscountIncompletaDTO)),
                                HttpStatus.OK);
        }

        @Operation(description = "Exclui um Desconto pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Desconto foi deletado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Desconto com o ID específicado")
        })
        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
                discountService.deleteDiscount(id);
                return ResponseEntity.ok().build();
        }

}
