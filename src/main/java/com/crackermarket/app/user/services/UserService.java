package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    // Search
    User findUserById(UUID id);
    User findUserByUserName(String userName);
    List<User> findUsersByName(String name);
    List<User> findAllUsers();
    List<User> findUsersBySurname(String surname);
    List<User> findUsersByEmail(String email);
    List<User> findUsersByPhoneNumber(String phoneNumber);
    List<User> findCustomersByAddress(Address address);

    // Updating table
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

}
