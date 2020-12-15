package com.crackermarket.app.shop.services;

import com.crackermarket.app.shop.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    public void save(Product product);
    public Product findById(UUID id);
    public List<Product> findByName(String name);
    public List<Product> findAll();
    public void delete(Product product);
}
