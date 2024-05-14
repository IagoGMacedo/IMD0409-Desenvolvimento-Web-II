package com.macedo.Ecommerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Integer id;
    private Integer idUser;
    private String cep;
    private String completeAddress; //endereco
    private String number;
    private String complement;
    private String district; //bairro
    private String city;
    private String state;

}
