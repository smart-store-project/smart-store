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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    private final IBrandService brandService;

    private final IProductService productService;

    public CategoryController(ICategoryService categoryService, IBrandService brandService, IProductService productService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
    }

    @ModelAttribute("brands")
    public List<Brand> brandList() {
        return brandService.findAll();
    }

    @GetMapping("")
    public ModelAndView showAllCategory() {
        ModelAndView view = new ModelAndView("category/list");
        view.addObject("categories", categoryService.findAll());
        return view;
    }

    @GetMapping("/{id}/view")
    public ModelAndView findCategory(@PathVariable Long id, Pageable pageable) {
        Category category = categoryService.findById(id);
        List<Product> products = productService.findAllByCategory(category);

        int page = Math.max(pageable.getPageNumber(), 0);
        pageable = PageRequest.of(page, 1);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), products.size());

        Page<Product> productsPage = new PageImpl<>(products.subList(start, end), pageable, products.size());

        ModelAndView view = new ModelAndView("category/category-view");
        view.addObject("category", category);
        view.addObject("products", productsPage);

        return view;
    }
    @GetMapping("/{id}/brand")
    public ModelAndView getCategoryByBrand(@PathVariable Long id) {
        Brand brand = brandService.findById(id);
        Iterable<Category> categories = categoryService.findAllByBrand(brand);
        ModelAndView view = new ModelAndView("brand1");
        view.addObject("categories", categories);
        return view;
    }
}
