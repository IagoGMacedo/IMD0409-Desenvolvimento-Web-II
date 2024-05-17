package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Product;
import com.macedo.Ecommerce.rest.dto.ProductDTO;

public interface ProductService {
    public ProductDTO findById(Integer id);
    public ProductDTO save(ProductDTO Product);
    public void delete(Integer id);
    public ProductDTO update(Integer id, ProductDTO Product);
    public List<ProductDTO> findAll(Product filtro);
    public ProductDTO patch(Integer id, ProductDTO ProductIncompletaDto);

    public List<ProductDTO> findByCategory(Integer categoryId);
}

