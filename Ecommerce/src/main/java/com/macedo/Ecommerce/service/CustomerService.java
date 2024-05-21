package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Customer;
import com.macedo.Ecommerce.rest.dto.CustomerDTO;

public interface CustomerService {
    public List<CustomerDTO> getCustomers(Customer filtro);

    public CustomerDTO getCustomerById(Integer id);

    public CustomerDTO createCustomer(CustomerDTO User);

    public CustomerDTO updateCustomer(Integer id, CustomerDTO User);

    public CustomerDTO patchCustomer(Integer id, CustomerDTO UserIncompletaDto);

    public void deleteCustomer(Integer id);
}
