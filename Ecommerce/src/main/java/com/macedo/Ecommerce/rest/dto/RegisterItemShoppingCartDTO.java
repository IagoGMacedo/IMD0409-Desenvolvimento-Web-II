package com.macedo.Ecommerce.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterItemShoppingCartDTO {
    @NotBlank(message = "{campo.idShoppingCart.obrigatorio}")
    private Integer idShoppingCart;
    @NotBlank(message = "{campo.idProductItem.obrigatorio}")
    private Integer idProductItem;
    private Integer quantity;


}
