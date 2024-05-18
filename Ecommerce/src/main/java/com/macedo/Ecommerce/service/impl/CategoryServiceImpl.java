package com.macedo.Ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Ecommerce.Utils.Patcher;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.repository.CategoryRepository;
import com.macedo.Ecommerce.rest.dto.CategoryDTO;
import com.macedo.Ecommerce.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Patcher patcher;

    @Override
    public CategoryDTO findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category"));
        return toDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO category) {
        Category newCategory = new Category();
        newCategory = extractCategory(category);
        return toDTO(categoryRepository.save(newCategory));
    }

    @Override
    public void delete(Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("category"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO update(Integer id, CategoryDTO category) {
        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("category"));

        Category newCategory = extractCategory(category);
        newCategory.setId(existingCategory.getId());
        return toDTO(categoryRepository.save(newCategory));
    }

    @Override
    public List<CategoryDTO> findAll(Category filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return toDTOList(categoryRepository.findAll(example));
    }

    @Override
    public CategoryDTO patch(Integer id, CategoryDTO categoryIncompletaDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category"));

        Category incompleteCategory = extractCategory(categoryIncompletaDto);

        patcher.copiarPropriedadesNaoNulas(incompleteCategory, existingCategory);
        return toDTO(categoryRepository.save(existingCategory));
    }

    private Category extractCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    private CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private List<CategoryDTO> toDTOList(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
