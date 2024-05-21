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

import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.rest.dto.AddressDTO;
import com.macedo.Ecommerce.rest.dto.ResponseCreditCardDTO;
import com.macedo.Ecommerce.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Operation(description = "Lista os endereços a partir de filtro, se passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddresses(AddressDTO filtro) {
        return new ResponseEntity<List<AddressDTO>>((addressService.getAddresses(filtro)), HttpStatus.OK);
    }

    @Operation(description = "Busca endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o endereço com o ID específicado"),
            @ApiResponse(responseCode = "404", description = "Não existe um endereço com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Integer id) {
        return new ResponseEntity<AddressDTO>((addressService.getAddressById(id)), HttpStatus.OK);
    }

    @Operation(description = "Retorna os endereços do usuário solicitado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os endereços do usuário solicitado"),
            @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID especificado")
    })
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<AddressDTO>> getAddressesByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<AddressDTO>>(
                (addressService.getAddressesByCustomerId(id)),
                HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O endereço foi criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um usuário com o ID específicado")
    })
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.createAddress(Address)), HttpStatus.CREATED);
    }

    @Operation(description = "Atualiza um endereço com o método PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O endereço foi atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um endereço com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Integer id, @RequestBody @Valid AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.updateAddress(id, Address)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza um endereço com o método PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O endereço foi atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não existe um endereço com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<AddressDTO> patchAddress(@PathVariable Integer id,
            @RequestBody AddressDTO AddressIncompletaDTO) {
        return new ResponseEntity<AddressDTO>((addressService.patchAddress(id, AddressIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Exclui um endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O endereço foi excluído"),
            @ApiResponse(responseCode = "404", description = "Não existe um endereço com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

}
