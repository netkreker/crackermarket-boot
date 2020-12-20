package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    // Search address
    Address findAddressById(UUID id);
    Address findAddressByUserName(String username);

    List<Address> findAllAddresses(int page, int resultsInPage);
    List<Address> findAllUserAddresses(UUID user_id, int page, int resultsInPage);
    List<Address> findAddressesByPostalCode(String postalcode, int page, int resultsInPage);
    List<Address> findAddressesByCountry(String country, int page, int resultsInPage);
    List<Address> findAddressesByStreet(String street, int page, int resultsInPage);
    List<Address> findAddressesByCity(String city, int page, int resultsInPage);

    // Updating table
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(Address address);
}
