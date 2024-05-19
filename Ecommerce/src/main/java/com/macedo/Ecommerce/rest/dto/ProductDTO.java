package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.Category;

import jakarta.validation.constraints.NotBlank;
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
public class ProductDTO {
    private Integer id;
    @NotBlank(message = "{campo.name.obrigatorio}")
    private String name;
    @NotBlank(message = "{campo.description.obrigatorio}")
    private String description;
    @NotBlank(message = "{campo.price.obrigatorio}")
    private BigDecimal price;
    @NotBlank(message = "{campo.categories.obrigatorio}")
    private List<Integer> categories;
    @NotBlank(message = "{campo.stockQuantity.obrigatorio}")
    private Integer stockQuantity;

}
