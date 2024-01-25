package com.codegym.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {
    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    @GetMapping(value = {"/", "/home", "/user",""})
    public ModelAndView homepage() {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        return modelAndView;
    }

    @GetMapping(value = "/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("/admin");
        modelAndView.addObject("user", getPrincipal());
        return modelAndView;
    }

    @GetMapping(value = "/seller")
    public ModelAndView seller() {
        ModelAndView modelAndView = new ModelAndView("/seller");
        modelAndView.addObject("user", getPrincipal());
        return modelAndView;
    }

    @GetMapping("/accessDenied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView("/access_denied");
        modelAndView.addObject("user", getPrincipal());
        return modelAndView;
    }

}
