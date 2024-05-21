package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Address;
import com.macedo.Ecommerce.rest.dto.AddressDTO;

public interface AddressService {
    public List<AddressDTO> getAddresses(AddressDTO filtro);

    public AddressDTO getAddressById(Integer id);

    public List<AddressDTO> getAddressesByCustomerId(Integer id);

    public AddressDTO createAddress(AddressDTO Address);

    public AddressDTO updateAddress(Integer id, AddressDTO Address);

    public AddressDTO patchAddress(Integer id, AddressDTO AddressIncompletaDto);

    public void deleteAddress(Integer id);

}
