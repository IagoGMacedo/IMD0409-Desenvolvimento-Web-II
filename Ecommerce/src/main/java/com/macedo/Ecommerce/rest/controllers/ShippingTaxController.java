package com.macedo.Ecommerce.rest.controllers;

import java.util.List;

import com.macedo.Ecommerce.rest.dto.ShippingTaxDTO;
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

import com.macedo.Ecommerce.model.ShippingTax;
import com.macedo.Ecommerce.service.ShippingTaxService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/shippingTaxes")
@RestController
@RequiredArgsConstructor
public class ShippingTaxController {
        @Autowired
        private ShippingTaxService shippingTaxService;

        @Operation(description = "Lista os Fretes existentes a partir de filtro, se passado")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de Fretes conforme filtro"),
        })
        @GetMapping
        public ResponseEntity<List<ShippingTaxDTO>> getShippingTaxs(ShippingTaxDTO filtro) {
                return new ResponseEntity<List<ShippingTaxDTO>>((shippingTaxService.getShippingTaxes(filtro)),
                                HttpStatus.OK);
        }

        @Operation(description = "Busca Frete pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o Frete com o ID específicado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Frete com o ID específicado")
        })
        @GetMapping("{id}")
        public ResponseEntity<ShippingTaxDTO> getShippingTaxById(@PathVariable Integer id) {
                return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.getShippingTaxById(id)),
                                HttpStatus.OK);
        }

        @Operation(description = "Adiciona um novo Frete")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "O novo Frete foi criado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido")
        })
        @PostMapping
        public ResponseEntity<ShippingTaxDTO> createShippingTax(
                        @RequestBody @Valid ShippingTaxDTO ShippingTax) {
                return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.createShippingTax(ShippingTax)),
                                HttpStatus.CREATED);
        }

        @Operation(description = "Atualiza um Frete com o método PUT")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o Frete atualizada"),
                        @ApiResponse(responseCode = "400", description = "Algum dos campos obrigatórios não foi preenchido"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Frete com o ID específicado")
        })
        @PutMapping("{id}")
        public ResponseEntity<ShippingTaxDTO> updateShippingTax(@PathVariable Integer id,
                        @RequestBody @Valid ShippingTaxDTO ShippingTax) {
                return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.updateShippingTax(id, ShippingTax)),
                                HttpStatus.OK);
        }

        @Operation(description = "Atualiza um Frete com o método PATCH")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o Frete atualizada"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Frete com o ID específicado")
        })
        @PatchMapping("{id}")
        public ResponseEntity<ShippingTaxDTO> patchShippingTax(@PathVariable Integer id,
                        @RequestBody ShippingTaxDTO ShippingTaxIncompletaDTO) {
                return new ResponseEntity<ShippingTaxDTO>(
                                (shippingTaxService.patchShippingTax(id, ShippingTaxIncompletaDTO)),
                                HttpStatus.OK);
        }

        @Operation(description = "Exclui um Frete pelo ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "O Frete foi deletado"),
                        @ApiResponse(responseCode = "404", description = "Não existe um Frete com o ID específicado")
        })
        @DeleteMapping("{id}")
        public ResponseEntity<Void> deleteShippingTax(@PathVariable Integer id) {
                shippingTaxService.deleteShippingTax(id);
                return ResponseEntity.ok().build();
        }

}
