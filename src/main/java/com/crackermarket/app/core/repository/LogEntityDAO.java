package com.crackermarket.app.core.repository;

import com.crackermarket.app.core.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Component
public class LogEntityDAO {
    @Autowired
    private EntityManager entityManager;

    public LogEntity findById(UUID id) {
        entityManager.getTransaction().begin();
        LogEntity log = entityManager.find(LogEntity.class, id);
        entityManager.getTransaction().commit();
        return log;
    }
    public void save(LogEntity log) {
        entityManager.getTransaction().begin();
        entityManager.persist(log);
        entityManager.getTransaction().commit();
    }

    public List<LogEntity> findAll(){
        List<LogEntity> logs = null;
        entityManager.getTransaction().begin();
        logs = entityManager.createQuery("SELECT c FROM LogEntity c").getResultList();
        entityManager.getTransaction().commit();
        return logs;
    }

    public void update(LogEntity log) {
        entityManager.getTransaction().begin();
        entityManager.merge(log);
        entityManager.getTransaction().commit();
    }

    public void delete(LogEntity log) {
        entityManager.getTransaction().begin();
        entityManager.remove(log);
        entityManager.getTransaction().commit();
    }
}
