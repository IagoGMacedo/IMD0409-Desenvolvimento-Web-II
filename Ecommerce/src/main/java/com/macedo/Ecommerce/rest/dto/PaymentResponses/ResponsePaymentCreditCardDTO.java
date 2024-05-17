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
public class ResponsePaymentCreditCardDTO {
    private Integer idCreditCard;
    private Integer installments;

}
