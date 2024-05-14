package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.User;
import com.macedo.Ecommerce.rest.dto.UserDTO;

public interface UserService {
    public UserDTO findById(Integer id);
    public UserDTO save(UserDTO User);
    public void delete(Integer id);
    public UserDTO update(Integer id, UserDTO User);
    public List<UserDTO> findAll(User filtro);
    public UserDTO patch(Integer id, UserDTO UserIncompletaDto);    
}

