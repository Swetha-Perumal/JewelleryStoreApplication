package com.jewellery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewellery.dto.CategoryDTO;
import com.jewellery.model.Category;
import com.jewellery.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

 
    @PostMapping("/add/{userId}")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, @PathVariable Long userId) {
        Category createdCategory = categoryService.addCategory(category, userId);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{categoryId}/{userId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId,@PathVariable long userId) {
        String response = categoryService.deleteCategory(categoryId,userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}/{userId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@PathVariable Long userId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId,userId, categoryDTO);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> searchCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryService.searchCategoryByName(name);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> searchCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.searchCategoryById(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
}
