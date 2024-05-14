package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    public ShoppingCartDTO findById(Integer id);
    public ShoppingCartDTO save(ShoppingCartDTO ShoppingCart);
    public void delete(Integer id);
    public ShoppingCartDTO update(Integer id, ShoppingCartDTO ShoppingCart);
    public List<ShoppingCartDTO> findAll(ShoppingCart filtro);
    public ShoppingCartDTO patch(Integer id, ShoppingCartDTO ShoppingCartIncompletaDto);
}

