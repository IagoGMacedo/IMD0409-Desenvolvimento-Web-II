package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.rest.dto.AddressDTO;

public interface AddressService {
    public AddressDTO findById(Integer id);
    public AddressDTO save(AddressDTO Address);
    public void delete(Integer id);
    public AddressDTO update(Integer id, AddressDTO Address);
    public List<AddressDTO> findAll(Address filtro);
    public AddressDTO patch(Integer id, AddressDTO AddressIncompletaDto);
}

