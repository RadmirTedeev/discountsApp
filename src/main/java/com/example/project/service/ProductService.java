package com.example.project.service;

import com.example.project.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getProducts();

    Product getProductById (Long id);

    void saveProduct(Product product);

    void deleteProduct (Long id);

    Set<Product> findProductsWithoutDiscount(Long discountId);
}
