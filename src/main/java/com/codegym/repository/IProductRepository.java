package com.codegym.repository;

import com.codegym.model.Brand;
import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);

//    @Query(nativeQuery = true,
//            value = "SELECT * FROM product " +
//                    "INNER JOIN category ON product.category_id = category.id " +
//                    "INNER JOIN brand ON brand.id = category.brand_id " +
//                    "WHERE brand.name = :brandName")
//    List<Product> findProductByBrandName(@Param("brandName") String brandName);

    @Query(nativeQuery = true,
            value = "SELECT * FROM product " +
                    "INNER JOIN category ON product.category_id = category.id " +
                    "INNER JOIN brand ON category.brand_id = brand.id " +
                    "WHERE product.product_name like '%' || :keyword || '%' OR " +
                    "      category.name like '%' || :keyword || '%' OR " +
                    "      brand.name like '%' || :keyword || '%'",
            countProjection = "Product.id")
    Page<Product> searchProduct(@Param("keyword") String keyword, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM product " +
                    "INNER JOIN category ON product.category_id = category.id " +
                    "INNER JOIN brand ON category.brand_id = brand.id " +
                    "WHERE brand.name like CONCAT('%', :brandName, '%')",
            countProjection = "Product.id")
    Page<Product> searchProductByBrandName(@Param("brandName") String brandName, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM product " +
                    "INNER JOIN category ON product.category_id = category.id " +
                    "INNER JOIN brand ON category.brand_id = brand.id " +
                    "WHERE category.name like CONCAT('%', :categoryName, '%')",
            countProjection = "Product.id")
    Page<Product> searchProductByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);


    Page<Product> findProductByNameContaining(String name, Pageable pageable);

    Page<Product> findProductByCategoryContaining(Category category, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "JOIN p.category c " +
            "JOIN c.brand b " +
            "WHERE b = :brand")
    Page<Product> findProductByBrand(Brand brand, Pageable pageable);
}
