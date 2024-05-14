package com.macedo.Ecommerce.service.impl;

import java.util.List;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.repository.ShoppingCartRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.ShoppingCart;
import com.macedo.Ecommerce.rest.dto.ShoppingCartDTO;
import com.macedo.Ecommerce.service.ShoppingCartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final Patcher patcher;

    @Override
    public ShoppingCartDTO findById(Integer id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shoppingCart"));
        return toDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO save(ShoppingCartDTO ShoppingCart) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


    @Override
    public void delete(Integer id) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shoppingCart"));
        shoppingCartRepository.delete(shoppingCart);
    }

    @Override
    public ShoppingCartDTO update(Integer id, ShoppingCartDTO ShoppingCart) {
        ShoppingCart existingShoppingCart = shoppingCartRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shoppingCart"));

        ShoppingCart newShoppingCart = extractShoppingCart(ShoppingCart);
        newShoppingCart.setId(existingShoppingCart.getId());
        return toDTO(shoppingCartRepository.save(newShoppingCart));
    }

    @Override
    public List<ShoppingCartDTO> findAll(ShoppingCart filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(shoppingCartRepository.findAll(example));
    }

    @Override
    public ShoppingCartDTO patch(Integer id, ShoppingCartDTO ShoppingCartIncompletaDto) {
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shoppingCart"));

        ShoppingCart incompleteShoppingCart = extractShoppingCart(ShoppingCartIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteShoppingCart, existingShoppingCart);
        return toDTO(shoppingCartRepository.save(existingShoppingCart));
    }

    private ShoppingCart extractShoppingCart(ShoppingCartDTO dto){
        return null;
    }
    private ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDto'");
    }
    private List<ShoppingCartDTO> toDTOList(List<ShoppingCart> shoppingCarts){ return null;}
    
}
