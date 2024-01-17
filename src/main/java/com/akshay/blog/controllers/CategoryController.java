package com.akshay.blog.controllers;


import com.akshay.blog.payloads.ApiResponse;
import com.akshay.blog.payloads.CategoryDTO;
import com.akshay.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    ///create category
    @PostMapping("/create-category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO createdCategory = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    ///update category
    @PutMapping("/update-category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer categoryId){
        CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
    }

    ///delete category
    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully!!", true), HttpStatus.OK);
    }

    ///get category by id
    @GetMapping("/get-category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){
        CategoryDTO categoryDTO = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    ///get all categories
    @GetMapping("/get-all-category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categoryDTOList = this.categoryService.getAllCategories();
        return ResponseEntity.ok(categoryDTOList);
    }

    ///create multiple categories
    @PostMapping("/create-multiple-categories")
    public ResponseEntity<List<CategoryDTO>> createMultipleCategories(@Valid @RequestBody List<CategoryDTO> categoryDTOList){
        List<CategoryDTO> createdCategoriesList = this.categoryService.createMultipleCategories(categoryDTOList);
        return new ResponseEntity<List<CategoryDTO>>(createdCategoriesList, HttpStatus.CREATED);
    }

}
