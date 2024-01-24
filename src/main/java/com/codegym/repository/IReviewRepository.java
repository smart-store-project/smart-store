package com.codegym.repository;

import com.codegym.model.Product;
import com.codegym.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProduct(Product product, Pageable pageable);
}
