package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
@RequestMapping("/user/search")
public class SearchController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ModelAndView search(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") int page) {
//        ModelAndView modelAndView = new ModelAndView("/product/search");
//        Iterable<Product> products = productService.searchProduct_v2(keyword);
//        List<Product> productList = StreamSupport.stream(products.spliterator(), false).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
//        int pageSize = 2;
//        int startItem = page * pageSize;
//        List<Product> pageList;
//
//        if (startItem < productList.size()) {
//            int endItem = Math.min(startItem + 2, productList.size());
//            pageList = productList.subList(startItem, endItem);
//        } else {
//            pageList = Collections.emptyList();
//        }
//        Pageable pageable = PageRequest.of(page, 2);
//        Page<Product> productPage = new PageImpl<>(pageList, pageable, productList.size());
////        Pageable pageable = PageRequest.of(0, 10);
////        Page<Product> products = productService.searchByBrandName(keyword.trim().toLowerCase(),pageable);
//        modelAndView.addObject("products", products);
//        return modelAndView;
        return null;
    }
}
