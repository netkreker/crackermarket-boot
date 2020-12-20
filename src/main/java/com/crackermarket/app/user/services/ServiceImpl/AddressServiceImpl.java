package com.crackermarket.app.user.services.ServiceImpl;

import com.crackermarket.app.user.repository.AddressDAO;
import com.crackermarket.app.user.services.AddressService;
import com.crackermarket.app.user.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    AddressDAO addressDAO;

    @Autowired
    AddressServiceImpl(AddressDAO addressDAO){this.addressDAO = addressDAO;}
    AddressServiceImpl(){}

    @Override
    public Address findAddressById(UUID id) {
        return addressDAO.findAddressById(id);
    }

    @Override
    public Address findAddressByUserName(String username) {
        return addressDAO.findAddressByUserName(username);
    }

    @Override
    public List<Address> findAllAddresses(int page, int resultsInPage) {
        return addressDAO.findAllAddresses(page, resultsInPage);
    }

    @Override
    public List<Address> findAllUserAddresses(UUID user_id, int page, int resultsInPage) {
        return addressDAO.findAllUserAddresses(user_id, page, resultsInPage);
    }

    @Override
    public List<Address> findAddressesByPostalCode(String postalcode, int page, int resultsInPage) {
        return addressDAO.findAddressesByPostalCode(postalcode, page, resultsInPage);
    }

    @Override
    public List<Address> findAddressesByCountry(String country, int page, int resultsInPage) {
        return addressDAO.findAddressesByCountry(country, page, resultsInPage);
    }

    @Override
    public List<Address> findAddressesByStreet(String street, int page, int resultsInPage) {
        return addressDAO.findAddressesByStreet(street, page, resultsInPage);
    }

    @Override
    public List<Address> findAddressesByCity(String city, int page, int resultsInPage) {
        return addressDAO.findAddressesByCity(city, page, resultsInPage);
    }

    @Override
    public void saveAddress(Address address) {
        addressDAO.saveAddress(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressDAO.updateAddress(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressDAO.deleteAddress(address);
    }
}
