package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.rest.dto.AddressDTO;

public interface AddressService {
    public AddressDTO findById(Integer id);
    public AddressDTO createAddress(AddressDTO Address);
    public void deleteAddress(Integer id);
    public AddressDTO updateAddress(Integer id, AddressDTO Address);
    public List<AddressDTO> getAddresses(Address filtro);
    public AddressDTO patchAddress(Integer id, AddressDTO AddressIncompletaDto);
}

