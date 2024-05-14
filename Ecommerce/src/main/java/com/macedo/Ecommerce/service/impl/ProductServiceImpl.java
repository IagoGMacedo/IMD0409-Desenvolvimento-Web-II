package com.macedo.Ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.repository.CategoryRepository;
import com.macedo.Ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Ecommerce.model.Product;
import com.macedo.Ecommerce.rest.dto.ProductDTO;
import com.macedo.Ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final Patcher patcher;

    @Override
    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product"));
        return toDTO(product);
    }

    @Override
    public ProductDTO save(ProductDTO Product) {
        Product newProduct = new Product();
        newProduct = extractProduct(Product);
        return toDTO(productRepository.save(newProduct));
    }


    @Override
    public void delete(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("product"));
        productRepository.delete(product);
    }

    @Override
    public ProductDTO update(Integer id, ProductDTO Product) {
        Product existingProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("product"));

        Product newProduct = extractProduct(Product);
        newProduct.setId(existingProduct.getId());
        return toDTO(productRepository.save(newProduct));
    }

    @Override
    public List<ProductDTO> findAll(Product filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(productRepository.findAll(example));
    }

    @Override
    public ProductDTO patch(Integer id, ProductDTO ProductIncompletaDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product"));

        Product incompleteProduct = extractProduct(ProductIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteProduct, existingProduct);
        return toDTO(productRepository.save(existingProduct));
    }

    private Product extractProduct(ProductDTO dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        if(dto.getCategories() != null){
            product.setCategories(extractCategories(dto));
        }
        return product;
    }

    private List<Category> extractCategories(ProductDTO dto){
        return dto.getCategories().stream()
        .map(
            integer -> {
                Category category = categoryRepository
                    .findById(integer)
                    .orElseThrow(() -> new NotFoundException("category"));
                return category;
            }).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categories_name(extractCategories(product.getCategories()))
                .build();
    }
    private List<ProductDTO> toDTOList(List<Product> products){ 
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(
                product -> ProductDTO
                        .builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stockQuantity(product.getStockQuantity())
                        .categories_name(extractCategories(product.getCategories()))
                        .build()
        ).collect(Collectors.toList());
    }

    private String extractCategories(List<Category> categories){
        String strCategories = "";
        for (Category category : categories){
            strCategories+= category.getName()+",";
        }
        return strCategories.substring(0, strCategories.length() - 1);
    }
    
}
