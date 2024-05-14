package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductItemDTO {
    private Integer idProduct;
    private Integer quantity;

    /*
    private Integer id;
    private Integer idPurchase;
     */
}
