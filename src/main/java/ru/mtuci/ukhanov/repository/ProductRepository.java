package ru.mtuci.ukhanov.repository;

import ru.mtuci.ukhanov.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}