package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.ProductItem;
import com.macedo.Ecommerce.rest.dto.ProductItemDTO;

public interface ProductItemService {
    public ProductItemDTO findById(Integer id);
    public ProductItemDTO save(ProductItemDTO ProductItem);
    public void delete(Integer id);
    public ProductItemDTO update(Integer id, ProductItemDTO ProductItem);
    public List<ProductItemDTO> findAll(ProductItem filtro);
    public ProductItemDTO patch(Integer id, ProductItemDTO ProductItemIncompletaDto);
}
