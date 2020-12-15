package com.crackermarket.app.shop.repository;

import com.crackermarket.app.shop.entities.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Component
public class ParameterDAO {

    @Autowired
    private EntityManager entityManager;

    public void save(Parameter parameter) {
        entityManager.getTransaction().begin();
        entityManager.persist(parameter);
        entityManager.getTransaction().commit();
    }

    public Parameter findById(UUID id) {
        entityManager.getTransaction().begin();
        Parameter parameter = entityManager.find(Parameter.class, id);
        entityManager.getTransaction().commit();
        return parameter;
    }

    public List<Parameter> findByName(String name) {
        List<Parameter> parameters = null;
        entityManager.getTransaction().begin();
        parameters = entityManager.createQuery("SELECT p FROM Parameter p WHERE p.name = :name")
                .setParameter("name", name).getResultList();
        entityManager.getTransaction().commit();
        return parameters;
    }
    public List<Parameter> findAll(){
        List<Parameter> parameters = null;
        entityManager.getTransaction().begin();
        parameters = entityManager.createQuery("SELECT p FROM Parameter p").getResultList();
        entityManager.getTransaction().commit();
        return parameters;
    }

    public void update(Parameter parameter) {
        entityManager.getTransaction().begin();
        entityManager.merge(parameter);
        entityManager.getTransaction().commit();
    }

    public void delete(Parameter parameter) {
        entityManager.getTransaction().begin();
        entityManager.remove(parameter);
        entityManager.getTransaction().commit();
    }
}
