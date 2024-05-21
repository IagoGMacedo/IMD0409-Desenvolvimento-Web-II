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

    @Operation(description = "Lista os Clientes existentes a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de Clientes conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(CustomerDTO filtro) {
        return new ResponseEntity<List<CustomerDTO>>((customerService.getCustomers(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca Cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o Cliente com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return new ResponseEntity<CustomerDTO>((customerService.getCustomerById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona um novo Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O novo Cliente foi criado"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.createCustomer(Customer)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza um Cliente com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Cliente foi atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
            @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id,
            @RequestBody @Valid CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.updateCustomer(id, Customer)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um Cliente com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Cliente foi atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Integer id,
            @RequestBody CustomerDTO CustomerIncompletaDTO) {
        return new ResponseEntity<CustomerDTO>((customerService.patchCustomer(id, CustomerIncompletaDTO)),
                HttpStatus.OK);
    }

    @Operation(description = "Exclui um Cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O Cliente foi deletado"),
            @ApiResponse(responseCode = "404", description = "Não existe um Cliente com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
