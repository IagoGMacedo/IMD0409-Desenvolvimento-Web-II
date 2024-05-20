package com.macedo.Ecommerce.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String password;
    @NotEmpty(message = "{campo.role.obrigatorio}")
    private String role;
}
