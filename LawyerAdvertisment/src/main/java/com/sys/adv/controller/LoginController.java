package com.sys.adv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class LoginController {
    @GetMapping("/login")
    ModelAndView login(ModelAndView modelAndView) {
    	modelAndView.setViewName("login/customLogin");
        return modelAndView;
    }
}