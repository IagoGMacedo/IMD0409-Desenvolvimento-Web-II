package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
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
public class ResponsePurchaseDTO {
    private Integer id;
    private Integer idUser;
    private List<ProductItemDTO> productItems;
    private ResponsePaymentDTO payment;
    private BigDecimal totalPrice;
    private Integer idAddress;
    private LocalDate date;
    private Integer idDiscount;
}
