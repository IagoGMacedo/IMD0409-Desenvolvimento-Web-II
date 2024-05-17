package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
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

import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;
import com.macedo.Ecommerce.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Operation(description = "Busca Payment pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Payment com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Payment com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsePaymentDTO> getPaymentById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Payment por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a Payment criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Payment não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<ResponsePaymentDTO> save(@RequestBody RegisterPaymentDTO Payment) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.save(Payment)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Payment pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A Payment foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Payment com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma Payment com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Payment atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Payment com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<ResponsePaymentDTO> update(@PathVariable Integer id, @RequestBody RegisterPaymentDTO Payment) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.update(id, Payment)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Payment com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Payment atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Payment com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<ResponsePaymentDTO> patch(@PathVariable Integer id, @RequestBody RegisterPaymentDTO PaymentIncompletaDTO) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.patch(id, PaymentIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as Payments existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de Payments conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ResponsePaymentDTO>> find(Payment filtro) {
        return new ResponseEntity<List<ResponsePaymentDTO>>((paymentService.findAll(filtro)), HttpStatus.OK);
    }
}
