package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Profile("products")
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

    public ProductDto payment(long productId, BigDecimal amount) {
        log.info("6. Calling payment from Product Service with productId {} and amount {}", productId, amount);
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must not be null or zero");
        }

        ProductEntity p = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found, id=" + productId));

        if (p.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance on productId=" + productId);
        }

        p.setBalance(p.getBalance().subtract(amount));
        ProductEntity saved = productRepository.save(p);
        return ProductDto.from(saved);
    }


}
