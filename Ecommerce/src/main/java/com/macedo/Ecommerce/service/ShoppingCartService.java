package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.AddItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.RegisterItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.ProductItemDTO;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    public ShoppingCartDTO findById(Integer id);

    public ShoppingCartDTO getShoppingCartByUserId(Integer userId);

    public List<ShoppingCartDTO> findAll(ShoppingCart filtro);

    public ShoppingCartDTO addItemToCart(AddItemShoppingCartDTO addProductItem);

    public void deleteItemFromCart(RegisterItemShoppingCartDTO deleteProductItem);

    public ShoppingCartDTO updateItemQuantity(RegisterItemShoppingCartDTO dto);
}

