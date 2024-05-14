package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private Role role;

}
