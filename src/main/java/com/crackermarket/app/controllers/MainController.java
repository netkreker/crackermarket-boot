package com.crackermarket.app.controllers;


import com.crackermarket.app.shop.entities.*;
import com.crackermarket.app.shop.services.CategoryService;
import com.crackermarket.app.shop.services.ParameterService;
import com.crackermarket.app.shop.services.ParameterValueService;
import com.crackermarket.app.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class MainController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ParameterService parameterService;
    @Autowired
    ProductService productService;
    @Autowired
    ParameterValueService parameterValueService;



    @GetMapping("/")
    public String sayHello() {
        return "index";
    }

    @GetMapping("/goodbye")
    public String sayGoodbye(){
        Category category = new Category();
        category.setName("TV");

        Product product = new Product();
        product.setName("MyTV");

        Parameter parameter = new Parameter();
        parameter.setName("Model");
        parameter.setParameterType(ParameterType.STRING);

        ParameterValue parameterValue = new ParameterValue();
        parameterValue.setParameter(parameter);
        parameterValue.setProduct(product);
        parameterValue.setParameterValue("Samsung QLED");

        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(14999.99));
        product.setCount(1);

        category.addParameter(parameter);

        categoryService.save(category);
        productService.save(product);
        parameterValueService.save(parameterValue);
        return "index";
    }
}
