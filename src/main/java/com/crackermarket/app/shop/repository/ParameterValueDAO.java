package com.crackermarket.app.shop.repository;

import com.crackermarket.app.shop.entities.ParameterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Component
public class ParameterValueDAO {

    @Autowired
    EntityManager entityManager;

    public void save(ParameterValue parameterValue) {
        entityManager.getTransaction().begin();
        entityManager.persist(parameterValue);
        entityManager.getTransaction().commit();
    }

    public ParameterValue findById(UUID id) {
        entityManager.getTransaction().begin();
        ParameterValue parameterValue = entityManager.find(ParameterValue.class, id);
        entityManager.getTransaction().commit();
        return parameterValue;
    }

    public List<ParameterValue> findByName(String name) {
        List<ParameterValue> parameterValues = null;
        entityManager.getTransaction().begin();
        parameterValues = entityManager.createQuery("SELECT pv FROM ParameterValue pv WHERE pv.name = :name")
                .setParameter("name", name).getResultList();
        entityManager.getTransaction().commit();
        return parameterValues;
    }
    public List<ParameterValue> findAll(){
        List<ParameterValue> parameterValues = null;
        entityManager.getTransaction().begin();
        parameterValues = entityManager.createQuery("SELECT pv FROM ParameterValue pv").getResultList();
        entityManager.getTransaction().commit();
        return parameterValues;
    }

    public void update(ParameterValue parameterValue) {
        entityManager.getTransaction().begin();
        entityManager.merge(parameterValue);
        entityManager.getTransaction().commit();
    }

    public void delete(ParameterValue parameterValue) {
        entityManager.getTransaction().begin();
        entityManager.remove(parameterValue);
        entityManager.getTransaction().commit();
    }
}
