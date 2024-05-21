package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.ResponseCreditCardDTO;
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

import com.macedo.Ecommerce.model.CreditCard;
import com.macedo.Ecommerce.rest.dto.RegisterCreditCardDTO;
import com.macedo.Ecommerce.service.CreditCardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/creditCards")
@RestController
@RequiredArgsConstructor
public class CreditCardController {
        @Autowired
        private CreditCardService creditCardService;

        @Operation(description = "Lista os Cartões existentes a partir de filtro, se passado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de Cartões conforme filtro"),
        })
        @GetMapping
        public ResponseEntity<List<ResponseCreditCardDTO>> getCreditCards(RegisterCreditCardDTO filtro) {
                return new ResponseEntity<List<ResponseCreditCardDTO>>((creditCardService.getCreditCards(filtro)),
                                HttpStatus.OK);
        }

        @Operation(description = "Busca Cartão pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o Cartão com o ID específicado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Cartão com o ID específicado")
        })
        @GetMapping("{id}")
        public ResponseEntity<ResponseCreditCardDTO> getCreditCardById(@PathVariable Integer id) {
                return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.getCreditCardById(id)),
                                HttpStatus.OK);
        }

        @Operation(description = "Busca os Cartões do cliente solicitado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna os Cartões do Cliente solicitado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
        })
        @GetMapping("/customer/{id}")
        public ResponseEntity<List<ResponseCreditCardDTO>> getCreditCardsByCustomerId(@PathVariable Integer id) {
                return new ResponseEntity<List<ResponseCreditCardDTO>>(
                                (creditCardService.getCreditCardsByCustomerId(id)),
                                HttpStatus.OK);
        }

        @Operation(description = "Adiciona uma nova CreditCard por DTO")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "O novo Cartão foi criado"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
                        @ApiResponse(responseCode = "404", description = "Não existe um cliente com o ID especificado")
        })
        @PostMapping
        public ResponseEntity<ResponseCreditCardDTO> createCreditCard(
                        @RequestBody @Valid RegisterCreditCardDTO CreditCard) {
                return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.createCreditCard(CreditCard)),
                                HttpStatus.CREATED);
        }

        @Operation(description = "Atualiza um Cartão com o método PUT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Cartão foi atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Cartão com o ID específicado")
        })
        @PutMapping("{id}")
        public ResponseEntity<ResponseCreditCardDTO> updateCreditCard(@PathVariable Integer id,
                        @RequestBody @Valid RegisterCreditCardDTO CreditCard) {
                return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.updateCreditCard(id, CreditCard)),
                                HttpStatus.OK);
        }

        @Operation(description = "Atualiza um Cartão com o método PATCH")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Cartão foi atualizado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Cartão com o ID específicado")
        })
        @PatchMapping("{id}")
        public ResponseEntity<ResponseCreditCardDTO> patchCreditCard(@PathVariable Integer id,
                        @RequestBody RegisterCreditCardDTO CreditCardIncompletaDTO) {
                return new ResponseEntity<ResponseCreditCardDTO>(
                                (creditCardService.patchCreditCard(id, CreditCardIncompletaDTO)),
                                HttpStatus.OK);
        }

        @Operation(description = "Exclui um Cartão pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Cartão foi deletado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Cartão com o ID específicado")
        })
        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteCreditCard(@PathVariable Integer id) {
                creditCardService.deleteCreditCard(id);
                return ResponseEntity.ok().build();
        }

}
