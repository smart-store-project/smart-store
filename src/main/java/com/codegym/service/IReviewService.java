package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReviewService extends IGenerateService<Review>{
    Page<Review> findAll(Pageable pageable);
    Page<Review> findByProduct(Product product, Pageable pageable);

}
