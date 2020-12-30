package com.crackermarket.app.user.repository;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
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
public class UserDAO {

    @Autowired
    private LogEntityService logService;

    @Autowired
    private EntityManager entityManager;

    public User findUserById(UUID id) {
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.getTransaction().commit();
        return user;
    }

    public void saveUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void updateUser(User user){
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public List<User> findUsersByName(String name, Integer page, Integer resultsInPage) {
        List<User> users = null;
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name order by u.id").setParameter("name", name);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findUsersByName", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + name + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByName", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByName", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

    /*public List<User> findAllUsers(){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        entityManager.getTransaction().commit();
        return users;
    }*/

    public List<User> findAllUsers(Integer page, Integer resultsInPage){

        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.id");

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllUsers", null, exception.getMessage(), null);
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
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUsers", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList(); //entityManager.createQuery("SELECT u FROM User u").getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUsers", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersBySurname(String surname, Integer page, Integer resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.surname =:surname order by u.id").setParameter("surname", surname);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findUsersBySurname", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + surname + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersBySurname", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersBySurname", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

    public User findUserByUserName(String userName){
        entityManager.getTransaction().begin();
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.userName =:userName").setParameter("userName", userName).getResultList();
        entityManager.getTransaction().commit();
        return users.get(0);
    }

    public void deleteUser(User user){
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public List<User> findUsersByEmail(String email, Integer page, Integer resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();
        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE u.email =:email").setParameter("email", email);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findUsersByEmail", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + email + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByEmail", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByEmail", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber, Integer page, Integer resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE u.phoneNumber =:phonenumber order by u.id").setParameter("phonenumber", phoneNumber);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findUsersByPhoneNumber", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + phoneNumber + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByPhoneNumber", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findUsersByPhoneNumber", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findCustomersByAddress(Address address, Integer page, Integer resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE id =:user_id order by u.id").setParameter("user_id", address.getUser().getId());

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findCustomersByAddress", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + address + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findCustomersByAddress", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        try {
            if (users == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findCustomersByAddress", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return users;
    }

}
