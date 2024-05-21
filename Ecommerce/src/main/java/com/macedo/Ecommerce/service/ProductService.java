package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Product;
import com.macedo.Ecommerce.rest.dto.ProductDTO;

public interface ProductService {
    public List<ProductDTO> getProducts(ProductDTO filtro);

    public ProductDTO getProductById(Integer id);

    public List<ProductDTO> getProductsByCategoryId(Integer categoryId);

    public ProductDTO createProduct(ProductDTO Product);

    public ProductDTO updateProduct(Integer id, ProductDTO Product);

    public ProductDTO patchProduct(Integer id, ProductDTO ProductIncompletaDto);

    public void deleteProduct(Integer id);

}
