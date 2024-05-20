package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPaymentDTO {
    @NotNull(message = "{campo.paymentMethod.obrigatorio}")
    private PaymentMethod paymentMethod;
    private Integer idCreditCard; //armazena se o pagamento tiver sido como cartão de credito;
    private Integer installments; //quantidade de parcelas de um pagamento
    private Integer idPurchase;

}
