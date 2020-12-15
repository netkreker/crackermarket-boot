package com.crackermarket.app.shop.repository;

import com.crackermarket.app.shop.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryDAO {
    @Autowired
    private EntityManager entityManager;

    public Category findById(UUID id) {
        entityManager.getTransaction().begin();
        Category category = entityManager.find(Category.class, id);
        entityManager.getTransaction().commit();
        return category;
    }
    public void save(Category category) {
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
    }

    public List<Category> findByName(String name) {
        List<Category> categories = null;
        entityManager.getTransaction().begin();
        categories = entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name")
                .setParameter("name", name).getResultList();
        entityManager.getTransaction().commit();
        return categories;
    }
    public List<Category> findAll(){
        List<Category> categories = null;
        entityManager.getTransaction().begin();
        categories = entityManager.createQuery("SELECT c FROM Category c").getResultList();
        entityManager.getTransaction().commit();
        return categories;
    }

    public void update(Category category) {
        entityManager.getTransaction().begin();
        entityManager.merge(category);
        entityManager.getTransaction().commit();
    }

    public void delete(Category category) {
        entityManager.getTransaction().begin();
        entityManager.remove(category);
        entityManager.getTransaction().commit();
    }
}
