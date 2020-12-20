package com.crackermarket.app.user.repository;

import com.crackermarket.app.user.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Component
public class AddressDAO {

    @Autowired
    private EntityManager entityManager;

    public Address findAddressById(UUID id) {
        entityManager.getTransaction().begin();
        Address address = entityManager.find(Address.class, id);
        entityManager.getTransaction().commit();
        return address;
    }
    public void saveAddress(Address address) {
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
    }

    public void updateAddress(Address address){
        entityManager.getTransaction().begin();
        entityManager.merge(address);
        entityManager.getTransaction().commit();
    }

    public void deleteAddress(Address address){
        entityManager.getTransaction().begin();
        entityManager.remove(address);
        entityManager.getTransaction().commit();
    }


    public List<Address> findAllAddresses(int page, int resultsInPage){
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a order by a.id");

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAllUserAddresses(UUID user_id, int page, int resultsInPage){
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.id=: user_id order by a.id").setParameter("user_id", user_id);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByPostalCode(String postalcode, int page, int resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.postalCode = :postalcode order by a.id")
                .setParameter("postalcode", postalcode);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByCountry(String country, int page, int resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.country = :country order by a.id")
                .setParameter("country", country);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByStreet(String street, int page, int resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.street = :street order by a.id")
                .setParameter("street", street);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByCity(String city, int page, int resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.city = :city order by a.id")
                .setParameter("city", city);

        if (query == null)
            return null;

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        entityManager.getTransaction().commit();
        return addresses;
    }

    public Address findAddressByUserName(String username){
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        addresses = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.userName = :username")
                .setParameter("username", username).getResultList();

        entityManager.getTransaction().commit();
        return addresses.get(0);
    }
}
