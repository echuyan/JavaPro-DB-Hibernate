package org.example.dto;

import org.example.entity.ProductEntity;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String accountNumber,
        BigDecimal balance,
        String type,
        Long userId) {
    public static ProductDto from(ProductEntity e) {
        return new ProductDto(
                e.getId(),
                e.getAccountNumber(),
                e.getBalance(),
                e.getProductType().name(),
                e.getUserId()
        );
    }


}
