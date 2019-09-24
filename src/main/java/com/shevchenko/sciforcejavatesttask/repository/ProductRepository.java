package com.shevchenko.sciforcejavatesttask.repository;

import com.shevchenko.sciforcejavatesttask.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductUuid(String uuid);
}
