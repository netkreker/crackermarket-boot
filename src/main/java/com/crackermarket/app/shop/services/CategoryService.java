package com.crackermarket.app.shop.services;

import com.crackermarket.app.shop.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public void save(Category category);
    public Category findById(String id);
    public List<Category> findByName(String name);
    public List<Category> findAll();
    public void delete(String id);
    public void update(Category category);
}
