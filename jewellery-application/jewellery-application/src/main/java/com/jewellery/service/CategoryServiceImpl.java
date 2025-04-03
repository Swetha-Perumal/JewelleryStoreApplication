package com.jewellery.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewellery.Utils.Role;
import com.jewellery.dto.CategoryDTO;
import com.jewellery.exception.AuthorizationException;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Category;
import com.jewellery.model.User;
import com.jewellery.repository.CategoryRepository;
import com.jewellery.repository.UserRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository crep;


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Category addCategory(Category category, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

	    if (!user.getRole().equals(Role.VENDOR)) {
	        throw new ResourceNotFoundException("Only vendors can add categories.");
	    }
		return crep.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId,Long userId) {
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

	    if (!user.getRole().equals(Role.VENDOR)) {
	        throw new ResourceNotFoundException("Only vendors can delete categories.");
	    }
	    
		Category category = crep.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		crep.delete(category);
		return "Category deleted successfully";
	}

	
	@Override
	public CategoryDTO updateCategory(Long categoryId,Long userId, CategoryDTO categoryDTO) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		if (user.getRole() != Role.VENDOR) {
			throw new AuthorizationException("Only Vendors can update categories");
		}
		
		 Category categorys = crep.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
		 
		 categorys.setCategoryName(categoryDTO.getCategoryName());
		 
	        Category updatedCategory = crep.save(categorys);
	        return modelMapper.map(updatedCategory,CategoryDTO.class);

	}

	
	@Override
	public CategoryDTO searchCategoryByName(String categoryName) {
		Category category = crep.findByCategoryName(categoryName)
				.orElseThrow(() -> new ResourceNotFoundException("Category with "+categoryName+" is  not found"));
		return modelMapper.map(category, CategoryDTO.class);
	}

	
	@Override
	public CategoryDTO searchCategoryById(Long categoryId) {
		Category category = crep.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with "+categoryId+" is not found"));
		CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}

}
