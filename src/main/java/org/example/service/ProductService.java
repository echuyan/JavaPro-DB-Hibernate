package org.example.service;

import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProductsByUserId(long userId) {
        return productRepository.findByUser_Id(userId);
    }

    public ProductEntity getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found, id=" + id));
    }


}
