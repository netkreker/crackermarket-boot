package com.crackermarket.app.user.services.ServiceImpl;

import com.crackermarket.app.core.Status;
import com.crackermarket.app.user.entities.Role;
import com.crackermarket.app.user.repository.UserDAO;
import com.crackermarket.app.user.services.UserService;
import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl() { }

    public User findUserById(UUID id) {
       return userDAO.findUserById(id);
    }
    public void saveUser(User user) {
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public List<User> findUsersByName(String name, int page, int resultsInPage) {
        return userDAO.findUsersByName(name, page, resultsInPage);
    }

    public List<User> findAllUsers(int page, int resultsInPage){
        return userDAO.findAllUsers(page, resultsInPage);
    }

    public List<User> findUsersBySurname(String surname, int page, int resultsInPage){
       return userDAO.findUsersBySurname(surname, page, resultsInPage);
    }

    public User findUserByUserName(String userName){
        return userDAO.findUserByUserName(userName);
    }

    public void deleteUser(User user){
        userDAO.deleteUser(user);
    }

    public List<User> findUsersByEmail(String email, int page, int resultsInPage){
        return userDAO.findUsersByEmail(email, page, resultsInPage);
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber, int page, int resultsInPage){
       return userDAO.findUsersByPhoneNumber(phoneNumber, page, resultsInPage);
    }

    public List<User> findCustomersByAddress(Address address, int page, int resultsInPage){
        return userDAO.findCustomersByAddress(address, page, resultsInPage);
    }


}
