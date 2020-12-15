package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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

    public List<User> findUsersByName(String name) {
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name")
                .setParameter("name", name).getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findAllUsers(){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u").getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersBySurname(String surname){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u WHERE u.surname =:surname").setParameter("surname", surname).getResultList();
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

    public List<User> findUsersByEmail(String email){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u WHERE u.email =:email").setParameter("email", email).getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u WHERE u.phoneNumber =:phonenumber").setParameter("phonenumber", phoneNumber).getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

    public List<User> findCustomersByAddress(Address address){
        List<User> users = null;
        entityManager.getTransaction().begin();
        users = entityManager.createQuery("SELECT u FROM User u WHERE id =:user_id").setParameter("user_id", address.getUser().getId()).getResultList();
        entityManager.getTransaction().commit();
        return users;
    }

}
