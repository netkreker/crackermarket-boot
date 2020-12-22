package com.crackermarket.app.shop.services;

import com.crackermarket.app.shop.entities.Product;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    void save(Product product);
    Product findById(String id);
    List<Product> findByName(String name);
    List<Product> findAll();
    List<Product> findAll(Integer i, Integer j);
    void delete(String id);
    void update(Product product);
}
