package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.AddItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.RegisterItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    public List<ShoppingCartDTO> getShoppingCarts(ShoppingCart filtro);

    public ShoppingCartDTO getShoppingCartById(Integer id);

    public ShoppingCartDTO getShoppingCartByUserId(Integer userId);

    public ShoppingCartDTO addItemToCart(AddItemShoppingCartDTO addProductItem);

    public ShoppingCartDTO updateItemQuantity(RegisterItemShoppingCartDTO dto);

    public void deleteItemFromCart(RegisterItemShoppingCartDTO deleteProductItem);
}
