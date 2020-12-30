package com.crackermarket.app.user.repository;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.entities.Feedback;
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
public class FeedbackDAO {

    @Autowired
    private LogEntityService logService;

    @Autowired
    private EntityManager entityManager;

    public Feedback findFeedbackById(UUID id){
        entityManager.getTransaction().begin();
        Feedback feedback = entityManager.find(Feedback.class, id);
        entityManager.getTransaction().commit();
        return feedback;
    }

    public List<Feedback> findAllFeedbacks(Integer page, Integer resultsInPage){
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT f FROM Feedback f order by f.id");

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllFeedbacks", null, exception.getMessage(), null);
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
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        List<Feedback> feedbacks = query.getResultList();

        try {
            if (feedbacks == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return feedbacks;
    }

    public List<Feedback> findAllUserFeedbacks(UUID user_id, Integer page, Integer resultsInPage){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.user.id =: user_id order by f.id").setParameter("user_id", user_id.toString());

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllUserFeedbacks", null, exception.getMessage(), null);
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
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUserFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        List<Feedback> feedbacks = query.getResultList();

        try {
            if (feedbacks == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUserFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return feedbacks;
    }

    public List<Feedback> findAllProductFeedbacks(UUID product_id, Integer page, Integer resultsInPage){
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.product.id =: product_id order by f.id").setParameter("product_id", product_id.toString());

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllProductFeedbacks", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + product_id + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllProductFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        List<Feedback> feedbacks = query.getResultList();

        try {
            if (feedbacks == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllProductFeedbacks", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

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
