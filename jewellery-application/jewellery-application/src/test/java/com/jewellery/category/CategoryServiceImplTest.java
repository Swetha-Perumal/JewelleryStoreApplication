package com.jewellery.category;

import com.jewellery.Utils.Role;
import com.jewellery.dto.CategoryDTO;
import com.jewellery.exception.AuthorizationException;
import com.jewellery.exception.ResourceNotFoundException;
import com.jewellery.model.Category;
import com.jewellery.model.User;
import com.jewellery.repository.CategoryRepository;
import com.jewellery.repository.UserRepository;
import com.jewellery.service.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private Category category;
    private CategoryDTO categoryDTO;
    private User vendorUser;
    private User customerUser;

    @BeforeEach
    void setUp() {
        vendorUser = new User();
        vendorUser.setUserid(1L);
        vendorUser.setRole(Role.VENDOR);

        customerUser = new User();
        customerUser.setUserid(2L);
        customerUser.setRole(Role.CUSTOMER);

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Gold");

        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Gold");
    }

    @Test
    void testAddCategory_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.addCategory(category, 1L);

        assertNotNull(savedCategory);
        assertEquals("Gold", savedCategory.getCategoryName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testAddCategory_Failure_NotVendor() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.addCategory(category, 2L);
        });

        assertEquals("Only vendors can add categories.", exception.getMessage());
    }

    @Test
    void testDeleteCategory_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        String result = categoryService.deleteCategory(1L, 1L);

        assertEquals("Category deleted successfully", result);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_Failure_NotVendor() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(1L, 2L);
        });

        assertEquals("Only vendors can delete categories.", exception.getMessage());
    }

    @Test
    void testDeleteCategory_Failure_CategoryNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(1L, 1L);
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void testUpdateCategory_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO updatedCategory = categoryService.updateCategory(1L, 1L, categoryDTO);

        assertNotNull(updatedCategory);
        assertEquals("Gold", updatedCategory.getCategoryName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testUpdateCategory_Failure_NotVendor() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(customerUser));

        Exception exception = assertThrows(AuthorizationException.class, () -> {
            categoryService.updateCategory(1L, 2L, categoryDTO);
        });

        assertEquals("Only Vendors can update categories", exception.getMessage());
    }

    @Test
    void testUpdateCategory_Failure_CategoryNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(vendorUser));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(1L, 1L, categoryDTO);
        });

        assertEquals("Category not found with id 1", exception.getMessage());
    }

    @Test
    void testSearchCategoryByName_Success() {
        when(categoryRepository.findByCategoryName("Gold")).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO foundCategory = categoryService.searchCategoryByName("Gold");

        assertNotNull(foundCategory);
        assertEquals("Gold", foundCategory.getCategoryName());
    }

    @Test
    void testSearchCategoryByName_Failure() {
        when(categoryRepository.findByCategoryName("Silver")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.searchCategoryByName("Silver");
        });

        assertEquals("Category with Silver is  not found", exception.getMessage());
    }

    @Test
    void testSearchCategoryById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO foundCategory = categoryService.searchCategoryById(1L);

        assertNotNull(foundCategory);
        assertEquals("Gold", foundCategory.getCategoryName());
    }

    @Test
    void testSearchCategoryById_Failure() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.searchCategoryById(2L);
        });

        assertEquals("Category with 2 is not found", exception.getMessage());
    }
}
