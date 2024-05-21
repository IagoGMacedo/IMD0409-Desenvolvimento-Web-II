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

import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.rest.dto.CustomerDTO;
import com.macedo.Ecommerce.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Operation(description = "Lista as Customers existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Customers conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(Customer filtro) {
        return new ResponseEntity<List<CustomerDTO>>((customerService.getCustomers(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca Customer pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Customer com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Customer com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return new ResponseEntity<CustomerDTO>((customerService.getCustomerById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Customer por DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a Customer criada"),
            @ApiResponse(responseCode = "404", description = "O usuário atrelado à Customer não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.createCustomer(Customer)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza uma Customer com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Customer atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Customer com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id, @RequestBody @Valid CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.updateCustomer(id, Customer)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Customer com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a Customer atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Customer com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Integer id, @RequestBody CustomerDTO CustomerIncompletaDTO) {
        return new ResponseEntity<CustomerDTO>((customerService.patchCustomer(id, CustomerIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Exclui uma Customer pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A Customer foi deletada"),
            @ApiResponse(responseCode = "404", description = "Não existe uma Customer com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
