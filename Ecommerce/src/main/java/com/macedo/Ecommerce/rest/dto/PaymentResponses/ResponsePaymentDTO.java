package com.macedo.Ecommerce.rest.dto.PaymentResponses;

import com.macedo.Ecommerce.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentDTO {
    private Integer id;
    private PaymentMethod paymentMethod;
    private BigDecimal price;
    private ResponsePaymentCreditCardDTO creditCard;
    private ResponsePaymentDebitCardDTO debitCard;

}
