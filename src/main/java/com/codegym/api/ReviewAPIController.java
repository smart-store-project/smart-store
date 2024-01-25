package com.codegym.api;

import com.codegym.model.Product;
import com.codegym.model.Review;
import com.codegym.model.dto.UserDto;
import com.codegym.service.IProductService;
import com.codegym.service.IReviewService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reviews")
public class ReviewAPIController {
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IUserService2 userService2;

    @Autowired
    private IProductService productService;

    @ModelAttribute("user")
    private UserDto currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDto) session.getAttribute("user");
    }

    @PostMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Page<Review>> addReview(@PathVariable("id") Long id,
                                                  @RequestBody Review review,
                                                  @ModelAttribute("user") UserDto userDto) {
        review.setDate(Timestamp.from(Instant.now()));
        review.setUser(userService2.convertUserDtoToUser(userDto).orElse(null));
        Product product = productService.findById(id);
        review.setProduct(product);
        reviewService.save(review);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Review> reviews = reviewService.findByProduct(product, pageable);
        return new ResponseEntity<>(reviews, HttpStatus.OK);

    }
}
