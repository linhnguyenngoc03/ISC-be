package com.example.demo.controller;

import com.example.demo.repository.CategoryRepository;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping("/allCategory")

    public ResponseEntity<Object> getAllCategory() throws Exception {
        List<Category> categoryList = CategoryRepository.getAllCategory();
        if (categoryList.size() != 0) {
            return ResponseEntity.ok().body(categoryList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/searchById")
    public ResponseEntity<Object> searchById(@RequestParam int categoryId) throws Exception {
        Category category = CategoryRepository.getCategoryById(categoryId);
        if (category.getCategoryId() != 0) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) throws Exception {
        if (CategoryRepository.createCategory(category)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
    @PatchMapping("/updateCategory")
    public ResponseEntity<Object> updateCategory(@RequestBody Category category) throws Exception {
        if (CategoryRepository.updateCategory(category)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Object> deleteCategory(@RequestParam int[] categoryId) throws Exception {
        if (CategoryRepository.deleteCategory(categoryId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCategoryAndProduct")
    public ResponseEntity<Object> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = CategoryRepository.getCategoryAndProduct();
        return ResponseEntity.ok().body(categoryAndProductList);
    }
}
