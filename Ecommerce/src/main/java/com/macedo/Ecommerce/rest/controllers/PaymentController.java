package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Ecommerce.model.Payment;
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

    @Operation(description = "Lista as Payments existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Payments conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<ResponsePaymentDTO>> getPayments(RegisterPaymentDTO filtro) {
        return new ResponseEntity<List<ResponsePaymentDTO>>((paymentService.getPayments(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca Payment pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Payment com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Payment com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponsePaymentDTO> getPaymentById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.getPaymentById(id)), HttpStatus.OK);
    }

    @Operation(description = "Retorna a lista de pagamentos do usuário solicitado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o carrinho de compras do usuário solicitado"),
            @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID especificado")
    })
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponsePaymentDTO>> getPaymentsByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePaymentDTO>>((paymentService.getPaymentsByCustomerId(id)), HttpStatus.OK);
    }
}
