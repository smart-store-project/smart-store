package com.codegym.controller.api;

import com.codegym.exception.DuplicateUsernameException;
import com.codegym.model.User;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IUserService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/users")
public class UserAPIController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserService2 userService2;

    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }


    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<String> isUserExist(@PathVariable("username") String username) {
        if (userService.isUserExisted(username)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.OK);
    }

    @GetMapping("/checkPass/{password}")
    public ResponseEntity<String> isValidPassword(@PathVariable("password") String password) {
        if (userService.isValidPassword(password)) {
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return new ResponseEntity<>("Add user successfully", HttpStatus.CREATED);
        } catch (DuplicateUsernameException e) {
            return new ResponseEntity<>("Add user fail", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/email")
    public ResponseEntity<?> addUserEmail(@RequestBody String email,
                                          @ModelAttribute("user") UserDto userDto) {
        userDto.setEmail(email);
        userService.save(userService2.convertUserDtoToUser(userDto).get());
        return ResponseEntity.ok("success");

    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<?> addUserPhoneNumber(@RequestBody String phoneNumber,
                                          @ModelAttribute("user") UserDto userDto) {
        userDto.setPhoneNumber(phoneNumber);
        userService.save(userService2.convertUserDtoToUser(userDto).get());
        return ResponseEntity.ok("success");
    }

    @PutMapping("/address")
    private ResponseEntity<?> updateAddress(@RequestBody String address,
                                            @ModelAttribute("user") UserDto userDto) {
        String newAddress = address.substring(1, address.length() - 1);
        userDto.setAddress(newAddress);
        User user = userService2.convertUserDtoToUser(userDto).orElse(null);
        assert user != null;
        user.setAddress(newAddress);
        userService.save(user);
        return ResponseEntity.ok("success");
    }
}
