package com.macedo.Ecommerce.rest.dto;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "{campo.idUser.obrigatorio}")
    private Integer idUser;
    @NotEmpty(message = "{campo.cep.obrigatorio}")
    private String cep;
    @NotEmpty(message = "{campo.completeAddress.obrigatorio}")
    private String completeAddress; //endereco
    @NotEmpty(message = "{campo.number.obrigatorio}")
    private String number;
    private String complement;
    @NotEmpty(message = "{campo.district.obrigatorio}")
    private String district; //bairro
    @NotEmpty(message = "{campo.city.obrigatorio}")
    private String city;
    @NotEmpty(message = "{campo.state.obrigatorio}")
    private String state;

}
