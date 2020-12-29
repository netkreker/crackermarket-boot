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
        try{
            entityManager.getTransaction().begin();
            Category category = entityManager.find(Category.class, id);
            entityManager.getTransaction().commit();
            return category;
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void save(Category category) {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(category);
            entityManager.getTransaction().commit();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public List<Category> findByName(String name) {
        try{
            List categories = null;
            entityManager.getTransaction().begin();
            categories = entityManager.createQuery("SELECT c FROM Category c WHERE LOWER(c.name) LIKE :name")
                    .setParameter("name", "%" + name.toLowerCase() + "%").getResultList();
            entityManager.getTransaction().commit();
            return categories;
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

    }

    public List<Category> findByName(String name, Integer page, Integer resultsInPage) {
        if(page == null || page < 0) {
            page = 0;
        }
        if(resultsInPage == null || resultsInPage < 0) {
            resultsInPage = 10;
        }
        List categories = null;

        try{
            entityManager.getTransaction().begin();
            categories = entityManager.createQuery("SELECT c FROM Category c WHERE LOWER(c.name) LIKE :name ORDER BY c.id")
                    .setParameter("name", "%" + name.toLowerCase() + "%")
                    .setFirstResult(page*resultsInPage)
                    .setMaxResults(resultsInPage)
                    .getResultList();
            entityManager.getTransaction().commit();
            return categories;
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

    }


    public List<Category> findAll(Integer page, Integer resultsInPage){
        if(page == null || page < 0) {
            page = 0;
        }
        if(resultsInPage == null || resultsInPage < 0) {
            resultsInPage = 10;
        }
        List categories = null;

        try{
            entityManager.getTransaction().begin();
            categories = entityManager.createQuery("SELECT c FROM Category c ORDER BY c.name")
                    .setFirstResult(page*resultsInPage)
                    .setMaxResults(resultsInPage)
                    .getResultList();
            entityManager.getTransaction().commit();
            return categories;
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

    }

    public List<Category> findAll(){
        List categories = null;

        try {
            entityManager.getTransaction().begin();
            categories = entityManager.createQuery("SELECT c FROM Category c ORDER BY c.name")
                    .getResultList();
            entityManager.getTransaction().commit();
            return categories;
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

    }

    public void update(Category category) {
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(category);
            entityManager.getTransaction().commit();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public void delete(Category category) {
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(category);
            entityManager.getTransaction().commit();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

    }
}
