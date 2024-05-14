package com.macedo.Ecommerce.rest.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDTO {
    private Integer id;
    private Integer idUser;
    private String cardHolderName;
    private String validity;
    private String number;
    private String lastNumbers;
    private String cvv;

}
