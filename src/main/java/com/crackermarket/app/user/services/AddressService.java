package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    // Search address
    Address findAddressById(UUID id);
    Address findAddressByUserName(String username);

    List<Address> findAllAddresses();
    List<Address> findAllUserAddresses(UUID user_id);
    List<Address> findAddressesByPostalCode(String postalcode);
    List<Address> findAddressesByCountry(String country);
    List<Address> findAddressesByStreet(String street);
    List<Address> findAddressesByCity(String city);

    // Updating table
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(Address address);
}
