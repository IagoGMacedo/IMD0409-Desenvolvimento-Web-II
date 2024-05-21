package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.rest.dto.CategoryDTO;

public interface CategoryService {
    public List<CategoryDTO> getCategories(CategoryDTO filtro);

    public CategoryDTO getCategoryById(Integer id);

    public CategoryDTO createCategory(CategoryDTO category);

    public CategoryDTO updateCategory(Integer id, CategoryDTO category);

    public CategoryDTO patchCategory(Integer id, CategoryDTO categoryIncompletaDto);

    public void deleteCategory(Integer id);

}
