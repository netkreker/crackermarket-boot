package com.crackermarket.app.shop.controllers;

import com.crackermarket.app.shop.entities.Parameter;
import com.crackermarket.app.shop.entities.ParameterType;
import com.crackermarket.app.shop.services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parameter")
public class ParameterController {

    @Autowired
    ParameterService parameterService;

    @GetMapping("/new")
    public String createNewParameter(Model model) {
        model.addAttribute("parameter", new Parameter());
        return "/parameters/parameter_creator";
    }

    @PostMapping("/create")
    public String saveParameter(@ModelAttribute("parameter") Parameter parameter,
                                @RequestParam(value = "type", required = false) String type) {
        switch (type) {
            case "text" : parameter.setParameterType(ParameterType.STRING); break;
            case "number" : parameter.setParameterType(ParameterType.INTEGER); break;
            case "logic" : parameter.setParameterType(ParameterType.BOOLEAN); break;
        }
        parameterService.save(parameter);
        return "redirect:/category/new";
    }
}
