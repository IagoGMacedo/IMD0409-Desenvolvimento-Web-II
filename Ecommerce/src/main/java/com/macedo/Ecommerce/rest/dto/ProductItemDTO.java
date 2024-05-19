package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductItemDTO {
    @NotBlank(message = "{campo.idProduct.obrigatorio}")
    private Integer idProduct;
    @NotBlank(message = "{campo.quantity.obrigatorio}")
    private Integer quantity;
    private BigDecimal subTotal;

}
