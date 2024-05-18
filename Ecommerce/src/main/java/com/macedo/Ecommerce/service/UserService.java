package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.rest.dto.UserDTO;

public interface UserService {
    public List<UserDTO> getUsers(User filtro);

    public UserDTO getUserById(Integer id);

    public UserDTO createUser(UserDTO User);

    public UserDTO updateUser(Integer id, UserDTO User);

    public UserDTO patchUser(Integer id, UserDTO UserIncompletaDto);

    public void deleteUser(Integer id);
}
