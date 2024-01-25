package com.codegym.controller;

import com.codegym.exception.DuplicateUsernameException;
import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.service.IRoleService;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @GetMapping()
    public ModelAndView showRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        User user = new User();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) throws DuplicateUsernameException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Validation error: {}" + error.getDefaultMessage());
            });
        }
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/user/register");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        userService.addUser(user);
        return new ModelAndView("redirect:/home");
    }
}
