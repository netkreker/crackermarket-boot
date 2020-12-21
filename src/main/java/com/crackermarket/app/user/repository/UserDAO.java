package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
public class UserDAO {

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

    public List<User> findUsersByName(String name, int page, int resultsInPage) {
        List<User> users = null;
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name order by u.id").setParameter("name", name);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();
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

    public List<User> findAllUsers(int page, int resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.id");

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList(); //entityManager.createQuery("SELECT u FROM User u").getResultList();

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersBySurname(String surname, int page, int resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.surname =:surname order by u.id").setParameter("surname", surname);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();
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

    public List<User> findUsersByEmail(String email, int page, int resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();
        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE u.email =:email").setParameter("email", email);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber, int page, int resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE u.phoneNumber =:phonenumber order by u.id").setParameter("phonenumber", phoneNumber);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findCustomersByAddress(Address address, int page, int resultsInPage){
        List<User> users = null;
        entityManager.getTransaction().begin();

        Query query =  entityManager.createQuery("SELECT u FROM User u WHERE id =:user_id order by u.id").setParameter("user_id", address.getUser().getId());

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        users = query.getResultList();

        entityManager.getTransaction().commit();
        return users;
    }

}
