package com.jewellery.service;

import com.jewellery.dto.CategoryDTO;
import com.jewellery.model.Category;

public interface CategoryService {

	
	
	public Category addCategory(Category category, Long userId);

	public String deleteCategory(Long categoryId,Long userId);

	public CategoryDTO updateCategory(Long categoryId,Long userId,CategoryDTO categoryDTO);

	public CategoryDTO searchCategoryByName(String name);

	public CategoryDTO searchCategoryById(Long categoryId);


	
}
