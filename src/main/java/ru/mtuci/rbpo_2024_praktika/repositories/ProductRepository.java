package ru.mtuci.rbpo_2024_praktika.repositories;

import com.mtuci.ukhanov.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}