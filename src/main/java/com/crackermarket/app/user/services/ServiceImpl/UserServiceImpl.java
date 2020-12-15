package com.crackermarket.app.user.services.ServiceImpl;

import com.crackermarket.app.user.repository.UserDAO;
import com.crackermarket.app.user.services.UserService;
import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserServiceImpl() { }

    public User findUserById(UUID id) {
       return userDAO.findUserById(id);
    }
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public List<User> findUsersByName(String name) {
        return userDAO.findUsersByName(name);
    }

    public List<User> findAllUsers(){
        return userDAO.findAllUsers();
    }

    public List<User> findUsersBySurname(String surname){
       return userDAO.findUsersBySurname(surname);
    }

    public User findUserByUserName(String userName){
        return userDAO.findUserByUserName(userName);
    }

    public void deleteUser(User user){
        userDAO.deleteUser(user);
    }

    public List<User> findUsersByEmail(String email){
        return userDAO.findUsersByEmail(email);
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber){
       return userDAO.findUsersByPhoneNumber(phoneNumber);
    }

    public List<User> findCustomersByAddress(Address address){
        return userDAO.findCustomersByAddress(address);
    }


}
