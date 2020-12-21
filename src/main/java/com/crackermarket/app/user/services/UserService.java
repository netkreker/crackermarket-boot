package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    // Search
    User findUserById(UUID id);
    User findUserByUserName(String userName);
    List<User> findUsersByName(String name, int page, int resultsInPage);
    List<User> findAllUsers(int page, int resultsInPage);
    List<User> findUsersBySurname(String surname, int page, int resultsInPage);
    List<User> findUsersByEmail(String email, int page, int resultsInPage);
    List<User> findUsersByPhoneNumber(String phoneNumber, int page, int resultsInPage);
    List<User> findCustomersByAddress(Address address, int page, int resultsInPage);

    // Updating table
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

}
