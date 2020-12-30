package com.crackermarket.app.user.repository;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.entities.Address;
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
public class AddressDAO {

    @Autowired
    private LogEntityService logService;

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


    public List<Address> findAllAddresses(Integer page, Integer resultsInPage){
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a order by a.id");

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllAddresses", null, exception.getMessage(), null);
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
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllAddresses", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllAddresses", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAllUserAddresses(UUID user_id, Integer page, Integer resultsInPage){

        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.id=: user_id order by a.id").setParameter("user_id", user_id);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAllUserAddresses", null, exception.getMessage(), null);
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
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUserAddresses", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAllUserAddresses", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByPostalCode(String postalcode, Integer page, Integer resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.postalCode = :postalcode order by a.id")
                .setParameter("postalcode", postalcode);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAddressesByPostalCode", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + postalcode + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByPostalCode", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByPostalCode", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByCountry(String country, Integer page, Integer resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.country = :country order by a.id")
                .setParameter("country", country);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAddressesByCountry", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + country + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByCountry", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByCountry", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByStreet(String street, Integer page, Integer resultsInPage) {
        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.street = :street order by a.id")
                .setParameter("street", street);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAddressesByStreet", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + street + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByStreet", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByStreet", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        entityManager.getTransaction().commit();
        return addresses;
    }

    public List<Address> findAddressesByCity(String city, Integer page, Integer resultsInPage) {

        List<Address> addresses = null;
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.city = :city order by a.id")
                .setParameter("city", city);

        try{

            try {
                if (page == null)
                    throw new BadPageNumberException("Page is null!");
                if (resultsInPage == null){
                    throw new BadResultsCountException("Results on page is null!");
                }
            }
            catch (Exception exception){
                LogEntity log = new LogEntity(LogEntityType.DEBUG, this.getClass(), "findAddressesByCity", null, exception.getMessage(), null);
                logService.save(log);
                exception.printStackTrace();
                return null;
            }

            if (query == null)
                throw new QueryNotFoundResultException("Can't find result set by parameter \"" + city + "\"");

            if (page < 0)
                throw new BadPageNumberException("Invalid page number: " + page);

            if (resultsInPage <= 0)
                throw new BadResultsCountException("Invalid count of results on page: " + resultsInPage);
        }
        catch(Exception exception) {
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByCity", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

        query.setFirstResult(page*resultsInPage);
        query.setMaxResults(resultsInPage);

        addresses = query.getResultList();

        try {
            if (addresses == null)
                throw new BadPageNumberException("Number of page " + page + " is too large!");
        }
        catch (Exception exception){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressesByCity", null, exception.getMessage(), null);
            logService.save(log);
            exception.printStackTrace();
            return null;
        }

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
