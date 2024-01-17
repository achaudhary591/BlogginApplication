package com.akshay.blog.services.implementations;

import com.akshay.blog.entities.Category;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.payloads.CategoryDTO;
import com.akshay.blog.reporsitories.CategoryRepository;
import com.akshay.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param categoryDTO 
     * @return
     */
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category addedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(addedCategory, CategoryDTO.class);
    }

    /**
     * @param categoryDTO 
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "Category ID", categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updatedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    /**
     * @param categoryId 
     */
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));
        this.categoryRepository.delete(category);
    }

    /**
     * @param categoryId 
     * @return
     */
    @Override
    public CategoryDTO getCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "category id", categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * @param categoryDTOList 
     * @return
     */
    @Override
    public List<CategoryDTO> createMultipleCategories(List<CategoryDTO> categoryDTOList) {

        List<Category> categories = categoryDTOList.stream().map(categoryDTO -> this.modelMapper.map(categoryDTO, Category.class)).collect(Collectors.toList());
        List<Category> savedCategories = this.categoryRepository.saveAll(categories);
        return savedCategories.stream().map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    /**
     * @return 
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = this.categoryRepository.findAll();

        return categoryList.stream().map((category) -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }
}
