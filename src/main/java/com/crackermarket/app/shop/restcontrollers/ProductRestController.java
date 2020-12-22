package com.crackermarket.app.shop.restcontrollers;


import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.entities.Product;
import com.crackermarket.app.shop.services.CategoryService;
import com.crackermarket.app.shop.services.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private LogEntityService logService;

    Logger logger = Logger.getLogger(CategoryRestController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<Product>> showAllProducts(@RequestParam(name = "page", required = false) Integer page,
                                                         @RequestParam(name = "maxResult", required = false) Integer maxResult) {

        List<Product> products = productService.findAll(page, maxResult);
        if(products.isEmpty()) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "showAllProducts", HttpStatus.NO_CONTENT, "Products not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Product> createCategory(@RequestBody Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(product.getName() != null && !"".equals(product.getName())) {
            productService.save(product);
            return new ResponseEntity<>(product, httpHeaders, HttpStatus.CREATED);
        } else {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "createCategory", HttpStatus.NO_CONTENT, "Product not saved", null);
            logService.save(log);
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Product> findProduct(@PathVariable String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        Product product = productService.findById(id);
        if(product != null) {
            return new ResponseEntity<>(product, httpHeaders,HttpStatus.OK);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Product> updateCategory(@RequestBody Product product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(product.getId() != null && product.getName() != null) {
            productService.update(product);
            return new ResponseEntity<>(product, httpHeaders, HttpStatus.OK);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(id != null && !"".equals(id)) {
            productService.delete(id);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}