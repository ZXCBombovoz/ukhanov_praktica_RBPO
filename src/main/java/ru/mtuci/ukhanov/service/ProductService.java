package ru.mtuci.ukhanov.service;


import ru.mtuci.ukhanov.model.Product;
import ru.mtuci.ukhanov.request.DataProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Long id);

    // save
    Product save(DataProductRequest request);

    // read
    List<Product> getAll();

    // update
    Product update(DataProductRequest request);

    // delete
    void delete(Long id);
}
