package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.AddItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.RegisterItemShoppingCartDTO;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    public List<ShoppingCartDTO> getShoppingCarts();

    public ShoppingCartDTO getShoppingCartById(Integer id);

    public ShoppingCartDTO getShoppingCartByCustomerId(Integer customerId);

    public ShoppingCartDTO addItemToCart(AddItemShoppingCartDTO addProductItem);

    public ShoppingCartDTO updateItemQuantity(RegisterItemShoppingCartDTO dto);

    public void deleteItemFromCart(RegisterItemShoppingCartDTO deleteProductItem);
}
