package com.macedo.Ecommerce.service;

import java.util.List;

import com.macedo.Ecommerce.model.Category;
import com.macedo.Ecommerce.rest.dto.CategoryDTO;

public interface CategoryService {
    public CategoryDTO findById(Integer id);
    public CategoryDTO save(CategoryDTO category);
    public void delete(Integer id);
    public CategoryDTO update(Integer id, CategoryDTO category);
    public List<CategoryDTO> findAll(Category filtro);
    public CategoryDTO patch(Integer id, CategoryDTO categoryIncompletaDto);
    
    
}
