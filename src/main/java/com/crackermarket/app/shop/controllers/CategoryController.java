package com.crackermarket.app.shop.controllers;

import com.crackermarket.app.shop.entities.Category;
import com.crackermarket.app.shop.entities.Parameter;
import com.crackermarket.app.shop.services.CategoryService;
import com.crackermarket.app.shop.services.ParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//@Controller
//@RequestMapping("/category")
public class CategoryController {
//    @Autowired
//    CategoryService categoryService;
//    @Autowired
//    ParameterService parameterService;
//    @Autowired
//    private EntityManager entityManager;
//
//    Logger logger = Logger.getLogger(CategoryController.class);
//
//    @GetMapping("/browser")
//    public String showAllCategories(Model model) {
//        model.addAttribute("categories", categoryService.findAll());
//        logger.info("/browse");
//        return "categories/category_browser";
//    }
//
//    @GetMapping("/new")
//    public String createCategory(Model model) {
//        model.addAttribute("category", new Category());
//        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("parameters", parameterService.findAll());
//        logger.info("/new");
//        return "/categories/category_creator";
//    }
//
//    @PostMapping("/create")
//    public String createCategory(@ModelAttribute("category") Category category,
//                                 @RequestParam(value = "parentCategoryId", required = false) String parentCategoryId,
//                                 @RequestParam(value = "parameterId", required = false) String parameterId) {
//        if(parentCategoryId != null && !"".equals(parentCategoryId)) {
//            category.setParentCategory(categoryService.findById(UUID.fromString(parentCategoryId)));
//        }
//        if(parameterId != null && !"".equals(parameterId)) {
//            Set<Parameter> parameterSet = new HashSet<>();
//            parameterSet.add(parameterService.findById(UUID.fromString(parameterId)));
//        }
//
//        categoryService.save(category);
//        return "redirect:/";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editEmployee(@PathVariable("id") String id, Model model) {
//        Category category = categoryService.findById(UUID.fromString(id));
//        model.addAttribute("category", category);
//
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("SELECT c FROM Category c WHERE c.id NOT IN (:selfId)");
//        query.setParameter("selfId", category.getId());
//        List<Category> parentCategories = query.getResultList();
//        entityManager.getTransaction().commit();
//
//        model.addAttribute("category", category);
//        model.addAttribute("parentCategories", parentCategories);
//        model.addAttribute("categoryId", category.getId());
//        return "/categories/category_editor";
//    }
//
//    @PostMapping("/update")
//    public String updateCategory(@RequestParam(value = "categoryName") String categoryName,
//                                 @RequestParam(value = "categoryId") String categoryId,
//                                 @RequestParam(value = "parentCategoryId") String parentCategoryId) {
//        Category category = new Category();
//        if(categoryId != null && !"".equals(categoryId)) {
//            category.setId(UUID.fromString(categoryId));
//        }
//        if(parentCategoryId != null && !"".equals(parentCategoryId)) {
//            category.setParentCategory(categoryService.findById(UUID.fromString(parentCategoryId)));
//        }
//        if(categoryName != null && !"".equals(categoryName)) {
//            category.setName(categoryName);
//        }
//        categoryService.update(category);
//        return "redirect:/category/browser";
//    }
}
