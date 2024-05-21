package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.ShippingTax;
import com.macedo.Ecommerce.rest.dto.ShippingTaxDTO;

public interface ShippingTaxService {
    public List<ShippingTaxDTO> getShippingTaxes(ShippingTax filtro);

    public ShippingTaxDTO getShippingTaxById(Integer id);

    public ShippingTaxDTO createShippingTax(ShippingTaxDTO shippingTax);

    public ShippingTaxDTO updateShippingTax(Integer id, ShippingTaxDTO shippingTax);

    public ShippingTaxDTO patchShippingTax(Integer id, ShippingTaxDTO shippingTaxIncompleteDto);

    public void deleteShippingTax(Integer id);
}
