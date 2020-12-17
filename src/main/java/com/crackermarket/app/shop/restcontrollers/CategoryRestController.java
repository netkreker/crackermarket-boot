package com.crackermarket.app.shop.restcontrollers;

import com.crackermarket.app.core.LogEntity;
import com.crackermarket.app.core.LogEntityType;
import com.crackermarket.app.core.services.LogEntityService;
import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.services.CategoryService;
import com.sun.javafx.geom.BaseBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LogEntityService logService;

    Logger logger = Logger.getLogger(CategoryRestController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> showAllCategories() {

        List<Category> categories = categoryService.findAll();
        if(categories.isEmpty()) {
            LogEntity log = new LogEntity(LogEntityType.ERROR, this.getClass(), "showAllCategories", HttpStatus.NO_CONTENT, "Categories not found", null);
            logService.save(log);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "showAllCategories", HttpStatus.FOUND, "Categories found", null);
            logService.save(log);
            return new ResponseEntity<>(categories, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println(category);
        if(category.getName() != null && !"".equals(category.getName())) {
            categoryService.save(category);
            LogEntity log = new LogEntity(LogEntityType.INFO, this.getClass(), "saveCategory", HttpStatus.CREATED, "Category with was created", null);
            logService.save(log);
            return new ResponseEntity<>(category, httpHeaders, HttpStatus.CREATED);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            Map<String, String> map = new HashMap<>();
            map.put("ErrorMEssage", "Message");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> findCategory(@PathVariable String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        Category category = categoryService.findById(id);
        if(category != null) {
            logger.info(HttpStatus.FOUND);
            return new ResponseEntity<>(category, httpHeaders,HttpStatus.FOUND);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(category.getId() != null && category.getName() != null) {
            categoryService.update(category);
            logger.info(HttpStatus.OK);
            return new ResponseEntity<>(category, httpHeaders, HttpStatus.OK);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> deleteCategory(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(id != null && !"".equals(id)) {
            categoryService.delete(id);
            logger.info(HttpStatus.OK);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } else {
            logger.error(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
