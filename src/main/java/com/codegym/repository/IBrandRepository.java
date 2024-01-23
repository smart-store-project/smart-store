package com.codegym.repository;

import com.codegym.model.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends PagingAndSortingRepository<Brand, Long> {

}
