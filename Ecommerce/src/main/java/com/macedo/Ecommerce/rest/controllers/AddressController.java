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
import com.macedo.Ecommerce.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Operation(description = "Busca Address pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Address com o ID específicado"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Address com o ID específicado")
    })
    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Integer id) {
        return new ResponseEntity<AddressDTO>((addressService.findById(id)), HttpStatus.OK);
    }

    @Operation(description = "Adiciona uma nova Address por DTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retorna a Address criada"),
        @ApiResponse(responseCode = "404", description = "O usuário atrelado à Address não foi encontrado")
    })
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.createAddress(Address)), HttpStatus.CREATED);
    }

    @Operation(description = "Exclui uma Address pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A Address foi deletada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Address com o ID específicado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Atualiza uma Address com o método PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Address atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Address com o ID específicado")
    })
    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Integer id, @RequestBody AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.updateAddress(id, Address)), HttpStatus.OK);
    }

    @Operation(description = "Atualiza uma Address com o método PATCH")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a Address atualizada"),
        @ApiResponse(responseCode = "404", description = "Não existe uma Address com o ID específicado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<AddressDTO> patchAddress(@PathVariable Integer id, @RequestBody AddressDTO AddressIncompletaDTO) {
        return new ResponseEntity<AddressDTO>((addressService.patchAddress(id, AddressIncompletaDTO)), HttpStatus.OK);
    }

    @Operation(description = "Lista as Addresss existentes a partir de filtro, se passado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de Addresss conforme filtro"),
    })
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddresses(Address filtro) {
        return new ResponseEntity<List<AddressDTO>>((addressService.getAddresses(filtro)), HttpStatus.OK);
    }
    
}
