package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Component
public class FeedbackDAO {

    @Autowired
    private EntityManager entityManager;

    public Feedback findFeedbackById(UUID id){
        entityManager.getTransaction().begin();
        Feedback feedback = entityManager.find(Feedback.class, id);
        entityManager.getTransaction().commit();
        return feedback;
    }

    public List<Feedback> findAllFeedbacks(){
        entityManager.getTransaction().begin();
        List<Feedback> feedbacks = entityManager.createQuery("SELECT f FROM Feedback f").getResultList();
        entityManager.getTransaction().commit();
        return feedbacks;
    }

    public List<Feedback> findAllUserFeedbacks(UUID user_id){
        entityManager.getTransaction().begin();
        List<Feedback> feedbacks = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.user.id =: user_id").setParameter("user_id", user_id.toString()).getResultList();
        entityManager.getTransaction().commit();
        return feedbacks;
    }

    public List<Feedback> findAllProductFeedbacks(UUID product_id){
        entityManager.getTransaction().begin();
        List<Feedback> feedbacks = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.product.id =: product_id").setParameter("product_id", product_id.toString()).getResultList();
        entityManager.getTransaction().commit();
        return feedbacks;
    }

    public void saveFeedback(Feedback feedback){
        entityManager.getTransaction().begin();
        entityManager.persist(feedback);
        entityManager.getTransaction().commit();
    }

    public void updateFeedback(Feedback feedback){
        entityManager.getTransaction().begin();
        entityManager.merge(feedback);
        entityManager.getTransaction().commit();
    }

    public void deleteFeedback(Feedback feedback){
        entityManager.getTransaction().begin();
        entityManager.remove(feedback);
        entityManager.getTransaction().commit();
    }

}
