package org.example.repository;

import org.example.entity.ProductEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("products")
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByUser_Id(Long userId);

}
