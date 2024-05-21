package com.macedo.Ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.*;
import com.macedo.Ecommerce.repository.CustomerRepository;
import com.macedo.Ecommerce.repository.ProductItemRepository;
import com.macedo.Ecommerce.repository.ProductRepository;
import com.macedo.Ecommerce.repository.ShoppingCartRepository;
import com.macedo.Ecommerce.rest.dto.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.service.ShoppingCartService;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    private final ProductItemRepository productItemRepository;
    private final CustomerRepository customerRepository;
    private final Patcher patcher;

    @Override
    public List<ShoppingCartDTO> getShoppingCarts() {
        return toDTOList(shoppingCartRepository.findAll());
    }

    @Override
    public ShoppingCartDTO getShoppingCartById(Integer id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shopping Cart"));
        return toDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO getShoppingCartByCustomerId(Integer customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Shopping Cart"));
        return toDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO addItemToCart(AddItemShoppingCartDTO addProductItem) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(addProductItem.getIdShoppingCart())
                .orElseThrow(() -> new NotFoundException("shopping cart"));

        ProductItem productItems = extractProductItem(shoppingCart, addProductItem.getProductItem());
        List<ProductItem> productItemsExisting = shoppingCart.getProductItems();
        productItemsExisting.add(productItems);
        shoppingCart.setProductItems(productItemsExisting);
        shoppingCartRepository.save(shoppingCart);
        return toDTO(shoppingCart);
    }

    @Override
    public void deleteItemFromCart(RegisterItemShoppingCartDTO deleteProductItem) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(deleteProductItem.getIdShoppingCart())
                .orElseThrow(() -> new NotFoundException("shopping cart"));

        ProductItem productItem = productItemRepository
                .findById(deleteProductItem.getIdProductItem())
                .orElseThrow(() -> new NotFoundException("product item"));

        List<ProductItem> productItemsExisting = shoppingCart.getProductItems();
        productItemsExisting.remove(productItem);
        shoppingCart.setProductItems(productItemsExisting);
        productItemRepository.delete(productItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDTO updateItemQuantity(RegisterItemShoppingCartDTO dto) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(dto.getIdShoppingCart())
                .orElseThrow(() -> new NotFoundException("shopping cart"));

        ProductItem productItem = productItemRepository
                .findById(dto.getIdProductItem())
                .orElseThrow(() -> new NotFoundException("product item"));

        if (dto.getQuantity() == 0) {
            this.deleteItemFromCart(dto);
        } else {
            productItem.setQuantity(dto.getQuantity());
            productItemRepository.save(productItem);
        }

        return toDTO(shoppingCart);
    }

    private ShoppingCart extractShoppingCart(ShoppingCartDTO dto) {
        return null;
    }

    private ProductItem extractProductItem(ShoppingCart shoppingCart, ProductItemDTO dto) {
        Integer idProduct = dto.getIdProduct();
        Product product = productRepository
                .findById(idProduct)
                .orElseThrow(() -> new NotFoundException("product"));

        ProductItem productItem = new ProductItem();
        productItem.setQuantity(dto.getQuantity());
        productItem.setShoppingCart(shoppingCart);
        productItem.setProduct(product);
        productItem.setSubTotal(product.getPrice().multiply(new BigDecimal(dto.getQuantity())));
        return productItem;
    }

    private ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        return ShoppingCartDTO.builder()
                .id(shoppingCart.getId())
                .idCustomer(shoppingCart.getCustomer().getId())
                .totalPrice(getTotalPrice(shoppingCart.getProductItems()))
                .productItems(toDTOProductItems(shoppingCart.getProductItems()))
                .build();
    }

    private List<ShoppingCartDTO> toDTOList(List<ShoppingCart> shoppingCarts) {
        if (CollectionUtils.isEmpty(shoppingCarts)) {
            return Collections.emptyList();
        }
        return shoppingCarts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private List<ProductItemDTO> toDTOProductItems(List<ProductItem> productItems) {
        if (CollectionUtils.isEmpty(productItems)) {
            return Collections.emptyList();
        }
        return productItems.stream().map(
                item -> ProductItemDTO
                        .builder()
                        .idProduct(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal())
                        .build())
                .collect(Collectors.toList());
    }

    private BigDecimal getTotalPrice(List<ProductItem> productItems) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (ProductItem productItem : productItems) {
            totalValue = totalValue
                    .add(productItem.getProduct().getPrice().multiply(new BigDecimal(productItem.getQuantity())));
        }
        return totalValue;
    }
}
