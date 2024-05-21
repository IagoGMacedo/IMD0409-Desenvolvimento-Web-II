package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPurchaseDTO {
    @NotNull(message = "{campo.idCustomer.obrigatorio}")
    private Integer idCustomer;
    private List<ProductItemDTO> productItems;
    private RegisterPaymentDTO payment;
    @NotNull(message = "{campo.idAddress.obrigatorio}")
    private Integer idAddress;
    private Integer idDiscount;
}
