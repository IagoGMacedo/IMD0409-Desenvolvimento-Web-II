package com.macedo.Ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.model.ShoppingCart;
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
    public List<ProductDTO> getProducts(ProductDTO filtro) {
        Product obj = extractProduct(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(productRepository.findAll(example));
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product"));
        return toDTO(product);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
        List<Product> list = productRepository.findByCategoriesId(categoryId)
                .orElseThrow(() -> new NotFoundException("category"));

        return toDTOList(list);
    }

    @Override
    public ProductDTO createProduct(ProductDTO Product) {
        Product newProduct = new Product();
        newProduct = extractProduct(Product);
        return toDTO(productRepository.save(newProduct));
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO Product) {
        Product existingProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("product"));

        Product newProduct = extractProduct(Product);
        newProduct.setId(existingProduct.getId());
        return toDTO(productRepository.save(newProduct));
    }

    @Override
    public ProductDTO patchProduct(Integer id, ProductDTO ProductIncompletaDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("product"));

        Product incompleteProduct = extractProduct(ProductIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteProduct, existingProduct);
        return toDTO(productRepository.save(existingProduct));
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("product"));
        productRepository.delete(product);
    }

    private Product extractProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        if (dto.getCategories() != null) {
            product.setCategories(extractCategories(dto));
        }
        return product;
    }

    private List<Category> extractCategories(ProductDTO dto) {
        return dto.getCategories().stream()
                .map(
                        integer -> {
                            Category category = categoryRepository
                                    .findById(integer)
                                    .orElseThrow(() -> new NotFoundException("category"));
                            return category;
                        })
                .collect(Collectors.toList());
    }

    private List<Integer> extractCategoriesIntegers(List<Category> categories) {
        List<Integer> integers = new ArrayList<Integer>();
        for (Category category : categories) {
            integers.add(category.getId());
        }
        return integers;
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categories(extractCategoriesIntegers(product.getCategories()))
                .build();
    }

    private List<ProductDTO> toDTOList(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
