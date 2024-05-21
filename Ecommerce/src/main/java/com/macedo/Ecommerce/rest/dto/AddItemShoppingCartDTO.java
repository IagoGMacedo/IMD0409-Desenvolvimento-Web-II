package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemShoppingCartDTO {
    @NotNull(message = "{campo.idShoppingCart.obrigatorio}")
    private Integer idShoppingCart;
    private Integer idCustomer;
    private ProductItemDTO productItem;
}
