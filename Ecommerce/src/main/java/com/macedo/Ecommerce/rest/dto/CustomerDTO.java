package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Integer id;
    @NotBlank(message = "{campo.name.obrigatorio}")
    private String name;
    @NotBlank(message = "{campo.email.obrigatorio}")
    @Email(message = "{campo.email.formato}")
    private String email;

}
