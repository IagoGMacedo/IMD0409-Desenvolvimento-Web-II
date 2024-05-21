package com.macedo.Ecommerce.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingTaxDTO {
    private Integer id;
    @NotNull(message = "{campo.state.obrigatorio}")
    @NotEmpty(message = "{campo.state.vazio}")
    private String state;
    @NotNull(message = "{campo.value.obrigatorio}")
    private BigDecimal value;
}
