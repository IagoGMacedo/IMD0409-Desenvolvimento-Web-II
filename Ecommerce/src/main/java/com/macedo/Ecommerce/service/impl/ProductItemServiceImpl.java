package com.macedo.Ecommerce.service.impl;

import java.util.List;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.repository.ProductItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.macedo.Ecommerce.model.ProductItem;
import com.macedo.Ecommerce.rest.dto.ProductItemDTO;
import com.macedo.Ecommerce.service.ProductItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final Patcher patcher;

    @Override
    public ProductItemDTO findById(Integer id) {
        ProductItem productItem = productItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("productItem"));
        return toDTO(productItem);
    }

    @Override
    public ProductItemDTO save(ProductItemDTO ProductItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


    @Override
    public void delete(Integer id) {
        ProductItem productItem = productItemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("productItem"));
        productItemRepository.delete(productItem);
    }

    @Override
    public ProductItemDTO update(Integer id, ProductItemDTO ProductItem) {
        ProductItem existingProductItem = productItemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("productItem"));

        ProductItem newProductItem = extractProductItem(ProductItem);
        newProductItem.setId(existingProductItem.getId());
        return toDTO(productItemRepository.save(newProductItem));
    }

    @Override
    public List<ProductItemDTO> findAll(ProductItem filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(productItemRepository.findAll(example));
    }

    @Override
    public ProductItemDTO patch(Integer id, ProductItemDTO ProductItemIncompletaDto) {
        ProductItem existingProductItem = productItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("productItem"));

        ProductItem incompleteProductItem = extractProductItem(ProductItemIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteProductItem, existingProductItem);
        return toDTO(productItemRepository.save(existingProductItem));
    }

    private ProductItem extractProductItem(ProductItemDTO dto){
        return null;
    }
    private ProductItemDTO toDTO(ProductItem productItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDto'");
    }
    private List<ProductItemDTO> toDTOList(List<ProductItem> productItems){ return null;}
    
}
