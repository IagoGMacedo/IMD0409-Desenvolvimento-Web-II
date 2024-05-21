package com.macedo.Ecommerce.rest.dto;

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
public class RegisterCreditCardDTO {
    @NotNull(message = "{campo.idCustomer.obrigatorio}")
    private Integer idCustomer;
    @NotBlank(message = "{campo.cardHolderName.obrigatorio}")
    private String cardHolderName;
    @NotBlank(message = "{campo.validity.obrigatorio}")
    private String validity;
    @NotBlank(message = "{campo.number.obrigatorio}")
    private String number;
    @NotBlank(message = "{campo.cvv.obrigatorio}")
    private String cvv;
    private String lastNumbers;

}
