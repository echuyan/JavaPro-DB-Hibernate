package org.example.controller;

import org.example.entity.ProductEntity;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/users/{userId}/products")
    public List<ProductEntity> getProductsByUserId(@PathVariable Long userId) {
        return productService.getAllProductsByUserId(userId);
    }

    @GetMapping("/products/{prodcutId}")
    public ProductEntity getProductById(@PathVariable Long prodcutId) {
        return productService.getProductById(prodcutId);
    }

}
