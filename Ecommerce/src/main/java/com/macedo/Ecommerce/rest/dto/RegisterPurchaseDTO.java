package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "{campo.idUser.obrigatorio}")
    private Integer idUser;
    private List<ProductItemDTO> productItems;
    private RegisterPaymentDTO payment;
    @NotBlank(message = "{campo.idAddress.obrigatorio}")
    private Integer idAddress;
}
