package com.codegym.controller;

import com.codegym.exception.DuplicateUsernameException;
import com.codegym.model.Notification;
import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.model.dto.UserDto;
import com.codegym.service.INotificationService;
import com.codegym.service.IRoleService;
import com.codegym.service.IUserService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserService2 userService2;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private INotificationService notificationService;

    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }



    @PostMapping("/update/address")
    public ModelAndView updateAddress(@RequestBody String address,
                                      @ModelAttribute("user") UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/account/address");
        User user = userService2.convertUserDtoToUser(userDto).orElse(null);
        assert user != null;
        user.setAddress(address);
        userService.save(user);
        return modelAndView;
    }

    @ExceptionHandler({DuplicateUsernameException.class})
    public ModelAndView duplicateUsernameException() {
        ModelAndView modelAndView = new ModelAndView("user/register");
        modelAndView.addObject("message", "Username or email or phone number already exists");
        return modelAndView;
    }

    @GetMapping({"/account/{type}", "/account"})
    public ModelAndView showAccountInfo(@PathVariable(value = "type", required = false) String type,
                                        @ModelAttribute("user") UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("user", userDto );
        if (type == null) {
            type = "profile";
        }
        modelAndView.addObject("type", type);
        return modelAndView;
    }

    @GetMapping("/notifications")
    public ModelAndView showNotifications(@ModelAttribute("user") UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView("/user/notification");
        Iterable<Notification> notifications = notificationService.findByUser(userDto);
        modelAndView.addObject("notifications", notifications);
        return modelAndView;
    }

}
