package com.crackermarket.app.user.repository;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.entities.Order;
import com.crackermarket.app.user.exceptions.BadPageNumberException;
import com.crackermarket.app.user.exceptions.BadResultsCountException;
import com.crackermarket.app.user.exceptions.QueryNotFoundResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
public class OrderDAO {

    @Autowired
    private LogEntityService logService;

    @Autowired
    EntityManager entityManager;

    public Order findOrderById(UUID id){
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        entityManager.getTransaction().commit();
        return order;
    }

    public List<Order> findAllOrders(Integer page, Integer resultsInPage){
        List<Order> orders = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT o FROM Order o order by o.id");

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllOrders", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllOrders", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        orders = query.getResultList();

        try {
            if (orders == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllOrders", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

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

    public List<Order> findUserOrders(UUID user_id, Integer page, Integer resultsInPage){
        List<Order> orders = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id=: user_id order by o.id").setParameter("user_id", user_id);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findUserOrders", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + user_id + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUserOrders", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        orders = query.getResultList();

        try {
            if (orders == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUserOrders", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return orders;
    }
}
