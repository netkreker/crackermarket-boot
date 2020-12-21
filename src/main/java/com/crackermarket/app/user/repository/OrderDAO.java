package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
public class OrderDAO {

    @Autowired
    EntityManager entityManager;

    public Order findOrderById(UUID id){
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        entityManager.getTransaction().commit();
        return order;
    }

    public List<Order> findAllOrders(int page, int resultsInPage){
        List<Order> orders = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT o FROM Order o order by o.id");

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        orders = query.getResultList();

        entityManager.getTransaction().commit();
        return orders;
    }

    public void saveOrder(Order order){
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
    }

    public void updateOrder(Order order){
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
    }

    public void deleteOrder(Order order){
        entityManager.getTransaction().begin();
        entityManager.remove(order);
        entityManager.getTransaction().commit();
    }

    public List<Order> findUserOrders(UUID user_id, int page, int resultsInPage){
        List<Order> orders = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id=: user_id order by o.id").setParameter("user_id", user_id);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        orders = query.getResultList();

        entityManager.getTransaction().commit();
        return orders;
    }
}
