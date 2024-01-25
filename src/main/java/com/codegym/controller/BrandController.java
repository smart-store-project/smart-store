package com.codegym.controller;

import com.codegym.model.Brand;
import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.IBrandService;
import com.codegym.service.ICategoryService;
import com.codegym.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/brands")
public class BrandController {
        private final IBrandService brandService;

        private final IProductService productService;

        private final ICategoryService categoryService;

        public BrandController(IBrandService brandService, IProductService productService, ICategoryService categoryService) {
            this.brandService = brandService;
            this.productService = productService;
            this.categoryService = categoryService;
        }

        @GetMapping("")
        public ModelAndView showAllBrand() {
            ModelAndView view = new ModelAndView("/brand/brand-view");
            view.addObject("brands", brandService.findAll());
            return view;
        }

    @GetMapping("/{id}/view")
    public ModelAndView viewBrand(@PathVariable Long id, Pageable pageable) {
        Brand brand = brandService.findById(id);
        List<Product> products = productService.findAllByBrand(brand);
        Iterable<Category> categories = categoryService.findAllByBrand(brand);

        int page = Math.max(pageable.getPageNumber(), 0);
        pageable = PageRequest.of(page, 1);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        Page<Product> productsPage = new PageImpl<>(products.subList(start, end), pageable, products.size());
        Page<Category> categoriesPage = new PageImpl<>(StreamSupport.stream(categories.spliterator(), false).collect(Collectors.toList()), pageable, products.size());

        ModelAndView view = new ModelAndView("brand/brand-view");
        view.addObject("brand", brand);
        view.addObject("products", productsPage);
        view.addObject("categories", categoriesPage);
        view.addObject("brandId", id);

        return view;
    }

    @GetMapping("/view/sort")
    public ModelAndView getSortedProductForBrand(@RequestParam String sortType, @RequestParam Long brandId, Pageable pageable) {
        Brand brand = brandService.findById(brandId);
        List<Product> sortedProducts;
        switch (sortType) {
            case "name":
                sortedProducts = brandService.sortProductByName(brandId);
                break;
            case "priceDesc":
                sortedProducts = brandService.sortProductsByPriceDesc(brandId);
                break;
            case "priceAsc":
                sortedProducts = brandService.sortProductsByPriceAsc(brandId);
                break;
            default:
                sortedProducts = productService.findAllByBrand(brand);
        }
        ModelAndView view = new ModelAndView("brand/brand-view");
        Iterable<Category> categories = categoryService.findAllByBrand(brand);
        int page = Math.max(pageable.getPageNumber(), 0);
        pageable = PageRequest.of(page, 1);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedProducts.size());
        Page<Product> productsPage = new PageImpl<>(sortedProducts.subList(start, end), pageable, sortedProducts.size());
        view.addObject("categories", categories);
        view.addObject("products", productsPage);
        view.addObject("sortType", sortType);
        view.addObject("brand", brand);
        view.addObject("brandId", brandId);
        return view;
    }
    @GetMapping("/{brandId}/view/category/{categoryId}")

    public ModelAndView viewProductsByBrandAndCategory(
            @PathVariable Long brandId,
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        Brand brand = brandService.findById(brandId);
        Category category = categoryService.findById(categoryId);
        Page<Product> products = productService.findProductsByBrandAndCategory(brand, category, pageable);
        ModelAndView view = new ModelAndView("brand/brand-view");
        view.addObject("brand", brand);
        view.addObject("products", products);
        return view;
    }
}
