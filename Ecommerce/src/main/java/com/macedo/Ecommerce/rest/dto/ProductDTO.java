package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "{campo.price.obrigatorio}")
    @Min(value = 1, message = "{campo.price.valormin}")
    private BigDecimal price;
    @NotNull(message = "{campo.categories.obrigatorio}")
    private List<Integer> categories;
    @NotNull(message = "{campo.stockQuantity.obrigatorio}")
    @Min(value = 0, message = "{campo.stockQuantity.valormin}")
    private Integer stockQuantity;

}
