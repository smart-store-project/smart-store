package com.codegym.service;

import com.codegym.model.Brand;
import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<Product> findAllByBrand(Brand brand);
    public Page<Product> findProductsByBrandAndCategory(Brand brand, Category category, Pageable pageable);
    List<Product> findAllByCategory(Category category);

    Product findById(Long id);

    Iterable<Product> findAll();

    List<Product> sortByName();

    List<Product> sortByIncreasePrice();

    List<Product> sortByDecreasePrice();

    Page<Product> searchProduct(String keyword, Pageable pageable);

    Page<Product> searchProductByCategoryName(String categoryName, Pageable pageable);

    Page<Product> searchProductByBrandName(String brandName, Pageable pageable);


    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductByName(String name, Pageable pageable);

    Page<Product> findProductByCategory(Category category, Pageable pageable);

    Page<Product> findProductByBrand(Brand brand, Pageable pageable);

}
