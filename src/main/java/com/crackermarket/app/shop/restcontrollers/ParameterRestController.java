package com.crackermarket.app.shop.restcontrollers;


import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.shop.entities.Parameter;
import com.crackermarket.app.shop.entities.Product;
import com.crackermarket.app.shop.services.ParameterService;
import com.crackermarket.app.shop.services.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parameter")
public class ParameterRestController {
    @Autowired
    private ParameterService parameterService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<Parameter>> showAllParameters() {

        List<Parameter> parameters = parameterService.findAll();
        if(parameters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(parameters, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Parameter> createCategory(@RequestBody Parameter parameter) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(parameter.getName() != null && !"".equals(parameter.getName())) {
            parameterService.save(parameter);
            return new ResponseEntity<>(parameter, httpHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Parameter> findParameter(@PathVariable String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        Parameter parameter = parameterService.findById(id);
        if(parameter != null) {
            return new ResponseEntity<>(parameter, httpHeaders,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Parameter> updateParameter(@RequestBody Parameter parameter) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(parameter.getId() != null && parameter.getName() != null) {
            parameterService.update(parameter);
            return new ResponseEntity<>(parameter, httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> deleteParameter(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(id != null && !"".equals(id)) {
            parameterService.delete(id);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}