package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView showLoginForm(@RequestParam(value = "error", defaultValue = "false") boolean error,
                                      @RequestParam(value = "logout", defaultValue = "false") boolean logout) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("/user/login");
        if (error) {
            modelAndView.addObject("error", "Wrong username or password");
        }
        if (logout) {
            modelAndView.addObject("logout", "Log out successfully");
        }
        return modelAndView;
    }


}
