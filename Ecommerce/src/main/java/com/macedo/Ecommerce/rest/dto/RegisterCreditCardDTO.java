package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCreditCardDTO {
    private Integer idUser;
    private String cardHolderName;
    private String validity;
    private String number;
    private String lastNumbers;
    private String cvv;

}
