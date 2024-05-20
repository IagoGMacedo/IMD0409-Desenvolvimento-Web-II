package com.macedo.Ecommerce.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class DiscountDTO {
    private Integer id;
    @NotNull(message = "{campo.rate.obrigatorio}")
    @Min(value = 1, message = "{campo.rate.valormin}")
    @Max(value=100, message = "{campo.rate.valormax}")
    private Integer rate;
}
