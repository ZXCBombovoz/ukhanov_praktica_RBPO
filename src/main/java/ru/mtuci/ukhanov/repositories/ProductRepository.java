package ru.mtuci.ukhanov.repositories;

import ru.mtuci.ukhanov.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}