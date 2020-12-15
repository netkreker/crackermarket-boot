package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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

    public List<Order> findAllOrders(){
        List<Order> orders = null;
        entityManager.getTransaction().begin();
        orders = entityManager.createQuery("SELECT o FROM Order o").getResultList();
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

    public List<Order> findUserOrders(UUID user_id){
        List<Order> orders = null;
        entityManager.getTransaction().begin();
        orders = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id=: user_id").setParameter("user_id", user_id).getResultList();
        entityManager.getTransaction().commit();
        return orders;
    }
}
