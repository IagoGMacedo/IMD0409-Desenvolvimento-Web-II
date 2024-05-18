package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.rest.dto.CustomerDTO;

public interface CustomerService {
    public List<CustomerDTO> getUsers(Customer filtro);

    public CustomerDTO getUserById(Integer id);

    public CustomerDTO createUser(CustomerDTO User);

    public CustomerDTO updateUser(Integer id, CustomerDTO User);

    public CustomerDTO patchUser(Integer id, CustomerDTO UserIncompletaDto);

    public void deleteUser(Integer id);
}
