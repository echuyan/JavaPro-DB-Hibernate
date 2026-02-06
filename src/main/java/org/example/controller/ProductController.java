package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequest;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.service.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Profile("products")
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

    @GetMapping("/products/{productId}")
    public ProductEntity getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/products/{productId}/payment")
    public ProductDto payment(@PathVariable Long productId, @RequestBody PaymentRequest request) {
        log.info("5. Calling Product Controller with productId {} and request {}", productId, request);
        return productService.payment(productId, request.getAmount());
    }

}
