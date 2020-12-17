package com.crackermarket.app.user.restcontrollers;

import com.crackermarket.app.core.StackTraceToStringConverter;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.user.services.UserService;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class SignUpUserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogEntityService logService;

    @GetMapping(value = "/new")
    public ModelAndView registrationUser() {
        ModelAndView registerPage = new ModelAndView("users/user_registration", "user", new User());
        return registerPage;
    }

    // Getters
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showAllUsers() {

        List<User> users = null;
        users = userService.findAllUsers();

        if (users == null || users.isEmpty()) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "showAllUsers", HttpStatus.NO_CONTENT, "Users not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "showAllUsers", HttpStatus.FOUND, "Users found", null);
        logService.save(log);
        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable String id){

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "getUserById", HttpStatus.NOT_FOUND, "User with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "getUserById", HttpStatus.FOUND, "User with id \'" + user.getId() + "\' found", null);
        logService.save(log);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    // Creator
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){

        if (user == null) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createUser", HttpStatus.BAD_REQUEST, "User not created", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "createUser", HttpStatus.CREATED, "User with id \'"
                + user.getId() + "\' was created", null);
        logService.save(log);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Put
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable(name = "id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "updateUser", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User oldUser = null;
        oldUser = userService.findUserById(UUID.fromString(id));

        if (oldUser == null) {

            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateUser", HttpStatus.NOT_FOUND, "User with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newUser == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateUser", HttpStatus.BAD_REQUEST, "User from request is invalid", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (oldUser.equals(newUser)) {
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateUser", HttpStatus.NOT_MODIFIED, "User with id \'" + id + "\' not modified", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        newUser.setId(UUID.fromString(id));
        userService.updateUser(newUser);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateUser", HttpStatus.OK, "User with id \'" + id + "\' was updated", null);
        logService.save(log);
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }

    // Delete
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "deleteUser", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "deleteUser", HttpStatus.NOT_FOUND, "User with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(user);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "deleteUser", HttpStatus.OK, "User with id \'" + id + "\' was deleted", null);
        logService.save(log);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
