package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductStatusRepository;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    //Get all product
    @GetMapping("/allProduct")
    public ResponseEntity<Object> getAllProduct() throws Exception {
        List<Product> productList = ProductRepository.getAllProduct();
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Object> searchByName(@RequestParam String productName) throws Exception {
        List<Product> productList = ProductRepository.searchByName(productName);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Object> getProductById(@RequestParam int productId) throws Exception {
        Product product = ProductRepository.getProductById(productId);
        if(product.getProductId() != 0) return ResponseEntity.ok().body(product);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<Object> filterByPrice(@RequestParam int from, int to) throws Exception {
        List<Product> productList = ProductRepository.sortByPrice(from, to);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<Object> filterByStatus(@RequestParam String status) throws Exception {
        List<Product> productList = ProductRepository.filterByStatus(status);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/multiFilter")
    public ResponseEntity<Object> multiFilter(@RequestParam(required = false) String categoryName, String price, String status) throws Exception {
        List<Product> productList = ProductRepository.multiFilter(categoryName, price, status);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) throws Exception {
        if(ProductRepository.createProduct(product)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Object>  deleteProduct(@RequestParam int[] productId) throws Exception {
        if(ProductRepository.deleteProductByChangingStatus(productId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updateProduct")
    public ResponseEntity<Object>  updateProduct(@RequestBody Product product) throws Exception {
        if(ProductRepository.updateProduct(product)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<Object> filterByCategory(@RequestParam int categoryId) throws Exception {
        List<Product> productList = ProductRepository.filterByCategoryId(categoryId);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getProductName")
    public ResponseEntity<Object> getProductName() throws Exception {
        String[] productName = ProductRepository.getProductName();
        if(productName != null) return ResponseEntity.ok().body(productName);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/allProductStatus")
    public ResponseEntity<Object> getAllProductStatus() throws Exception {
        List<ProductStatus> productStatusList = ProductStatusRepository.getAllProductStatus();
        if(productStatusList.size()>0) return ResponseEntity.ok().body(productStatusList);
        else return ResponseEntity.badRequest().build();
    }
}
