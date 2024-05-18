package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemShoppingCartDTO {
    private Integer idShoppingCart;
    private Integer idUser;
    private ProductItemDTO productItem;
}
