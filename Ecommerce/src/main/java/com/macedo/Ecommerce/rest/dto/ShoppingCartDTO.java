package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDTO {
    private Integer id;
    private Integer idCustomer;
    private List<ProductItemDTO> productItems;
    private BigDecimal totalPrice;
}
