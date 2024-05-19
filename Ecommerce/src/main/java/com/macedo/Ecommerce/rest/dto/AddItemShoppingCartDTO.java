package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemShoppingCartDTO {
    @NotBlank(message = "{campo.idShoppingCart.obrigatorio}")
    private Integer idShoppingCart;
    @NotBlank(message = "{campo.idUser.obrigatorio}")
    private Integer idUser;
    private ProductItemDTO productItem;
}
