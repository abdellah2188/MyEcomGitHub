
package com.hamch.productserviceb.controller;

import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.repository.ProductRepository;
import com.hamch.productserviceb.services.impl.CategoryService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/product/{id}")
     Optional<Product> findCategory(@PathVariable long id) {
        System.out.println("GGGGGGGGGGG"+ id);
         
         Optional<Product> p = productRepository.findById(id);
        System.out.println("WWWWWWWWWWWWWWWW"+ p);
        return p;
        //return inventory;
    }

}


