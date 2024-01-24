package com.codegym.service.impl;

import com.codegym.model.Product;
import com.codegym.model.Review;
import com.codegym.repository.IReviewRepository;
import com.codegym.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private IReviewRepository iReviewRepository;

    @Override
    public Iterable<Review> findAll() {
        return iReviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return iReviewRepository.findById(id);
    }

    @Override
    public void save(Review review) {
        iReviewRepository.save(review);
    }

    @Override
    public void remove(Long id) {
        iReviewRepository.deleteById(id);
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        return iReviewRepository.findAll(pageable);
    }

    @Override
    public Page<Review> findByProduct(Product product, Pageable pageable) {
        return iReviewRepository.findByProduct(product, pageable);
    }
}
