package com.macedo.Ecommerce.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterItemShoppingCartDTO {
    @NotNull(message = "{campo.idShoppingCart.obrigatorio}")
    private Integer idShoppingCart;
    @NotNull(message = "{campo.idProductItem.obrigatorio}")
    private Integer idProductItem;
    private Integer quantity;


}
