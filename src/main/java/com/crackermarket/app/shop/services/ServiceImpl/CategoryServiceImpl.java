package com.crackermarket.app.shop.services.ServiceImpl;

import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.repository.CategoryDAO;
import com.crackermarket.app.shop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public CategoryServiceImpl() {

    }

    @Override
    public void save(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public Category findById(String id) {
        return categoryDAO.findById(UUID.fromString(id));
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public List<Category> findAll(Integer i, Integer j) {
        return categoryDAO.findAll(i, j);
    }

    @Override
    public void delete(String id) {
        categoryDAO.delete(categoryDAO.findById(UUID.fromString(id)));
    }

    @Override
    public void update(Category category) {
        Category parent = findById(category.getParentCategory().getId().toString());
        category.copyParametersFromParentCategory(parent);
        categoryDAO.update(category);
    }
}
