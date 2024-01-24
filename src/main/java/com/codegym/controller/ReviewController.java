package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Review;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IProductService;
import com.codegym.service.IReviewService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;


@Controller
@RequestMapping("/user/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService2 userService2;

    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }

    @GetMapping("/create/{productId}")
    public ModelAndView addReviewForm(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        ModelAndView modelAndView;
        if (product != null) {
            modelAndView = new ModelAndView("/review/create");
            Review review = new Review();
            review.setProduct(product);
            modelAndView.addObject("review", review);
        } else {
            modelAndView = new ModelAndView("redirect:/products/list");
            modelAndView.addObject("message", "Product not found");
        }
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView addReview(
            @ModelAttribute("user") UserDto userDto,
            @Validated @ModelAttribute("review") Review review,
            BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/review/create");
        }

        review.setUser(userService2.convertUserDtoToUser(userDto).get());
        review.setDate(Timestamp.from(Instant.now()));
        reviewService.save(review);
        ModelAndView modelAndView = new ModelAndView("/product/comment");
        modelAndView.addObject("message", "Add review successfully");
        return modelAndView;
    }


}
