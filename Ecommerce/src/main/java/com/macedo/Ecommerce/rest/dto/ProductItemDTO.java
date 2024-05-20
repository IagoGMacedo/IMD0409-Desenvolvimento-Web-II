package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductItemDTO {
    @NotNull(message = "{campo.idProduct.obrigatorio}")
    private Integer idProduct;
    @NotNull(message = "{campo.quantity.obrigatorio}")
    private Integer quantity;
    private BigDecimal subTotal;

}
