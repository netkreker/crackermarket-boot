package com.crackermarket.app.controllers.address;

import com.crackermarket.app.user.repository.AddressDAO;
import com.crackermarket.app.user.repository.UserDAO;
import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    UserDAO userDAO;

    @GetMapping(value = "/form")
    public ModelAndView registrationUser() {
        ModelAndView addressPage = new ModelAndView("users/change_address");
        return addressPage;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getAddressByUserName(@PathVariable String userName){

        Address address = addressDAO.findAddressByUserName(userName);

        if (address == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(address, HttpStatus.FOUND);
    }

    @ResponseBody
    @PostMapping(value = "/new/{username}")
    public ResponseEntity<?> createAddressForUser(@RequestBody Address address, @PathVariable String username){

        if (address == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Adding user
        User user = userDAO.findUserByUserName(username);
        address.setUser(user);

        addressDAO.saveAddress(address);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

}
