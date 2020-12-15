package com.crackermarket.app.user.restcontrollers;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.StackTraceToStringConverter;
import com.crackermarket.app.user.entities.Address;
import com.crackermarket.app.user.entities.User;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.user.services.AddressService;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private LogEntityService logService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/users/{id}/address/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewAddress(@PathVariable(name="id") String id, @RequestBody Address address){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "createNewAddress", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (address == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createNewAddress", HttpStatus.BAD_REQUEST, "Address with not created", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = null;
        user = userService.findUserById(UUID.fromString(id));

        if (user == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createNewAddress", HttpStatus.NOT_FOUND, "User with id\'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        address.setUser(user);
        addressService.saveAddress(address);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "createNewAddress", HttpStatus.CREATED, "Address with id \'" + address.getId() + "\' created", null);
        logService.save(log);
        return new ResponseEntity<>(address.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{username}/address", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserAddress(@PathVariable(name = "username") String username){

        Address address = null;
        address = addressService.findAddressByUserName(username);

        if (address == null){

            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findUserAddress", HttpStatus.NOT_FOUND, "Address by username \'" + username + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findUserAddress", HttpStatus.FOUND, "Address by username \'" + username + "\' found", null);
        logService.save(log);
        return new ResponseEntity<>(address.getId(), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/address/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllAddresses(){

        List<Address> addresses = null;
        addresses = addressService.findAllAddresses();

        if (addresses == null || addresses.isEmpty()){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findAllAddresses", HttpStatus.NO_CONTENT, "Addresses not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UUID> addressesId = new ArrayList<>();

        for (Address address: addresses)
            addressesId.add(address.getId());

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findAllAddresses", HttpStatus.FOUND, "Addresses found", null);
        logService.save(log);

        return new ResponseEntity<>(addressesId, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAddressById(@PathVariable(name = "id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "findAddressById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address address = null;
        address = addressService.findAddressById(UUID.fromString(id));

        if (address == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "findAddressById", HttpStatus.NO_CONTENT, "Address with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "findAddressById", HttpStatus.FOUND, "Address with id \'" + id + "\' found", null);
        logService.save(log);

        return new ResponseEntity<>(address.getId(), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/address/{id}/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAddressById(@PathVariable(name = "id") String id, @RequestBody Address address){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "updateAddressById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (address == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateAddressById", HttpStatus.BAD_REQUEST, "Address from request is invalid", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address oldAddress = null;
        oldAddress = addressService.findAddressById(UUID.fromString(id));

        if (oldAddress == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "updateAddressById", HttpStatus.NOT_FOUND, "Address with id\'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (oldAddress.equals(address)){
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateAddressById", HttpStatus.NOT_MODIFIED, "Address with id\'" + id + "\' not modified", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        addressService.updateAddress(address);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "updateAddressById", HttpStatus.OK, "Address with id \'" + address.getId() + "\' modified", null);
        logService.save(log);
        return new ResponseEntity<>(address.getId(), HttpStatus.OK);
    }


    @RequestMapping(value = "/address/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAddressById(@PathVariable(name="id") String id){

        try{
            UUID.fromString(id);
        }
        catch(IllegalArgumentException ex){
            LogEntity log = new LogEntity(LogEntityType.FATAL, this.getClass(), "deleteAddressById", HttpStatus.BAD_REQUEST, "Id \'" + id + "\' is invalid", StackTraceToStringConverter.toString(ex));
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address address = null;
        address = addressService.findAddressById(UUID.fromString(id));

        if (address == null){
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "deleteAddressById", HttpStatus.NOT_FOUND, "Address with id \'" + id + "\' not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        addressService.deleteAddress(address);

        LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "deleteAddressById", HttpStatus.OK, "Address with id \'" + id + "\' deleted", null);
        logService.save(log);
        return new ResponseEntity<>(address.getId(), HttpStatus.OK);

    }
}
