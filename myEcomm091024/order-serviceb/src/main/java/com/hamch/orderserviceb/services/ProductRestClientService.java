package com.hamch.orderserviceb.services;

import com.hamch.orderserviceb.model.Product;
import com.hamch.orderserviceb.security.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-serviceb", url = "${product-serviceb.url}",configuration = {ClientConfiguration.class})
public interface ProductRestClientService {
    @GetMapping("/products/{id}?projection=fullProduct")
    public Product productById(@PathVariable Long id);

    @GetMapping("/products/{id}")
    public Product isInStock(@PathVariable Long id);

    @PutMapping("/products/{id}/{stock}")
    public Product productUpStock(@PathVariable Long id, int stock);

    @GetMapping("/products?projection=fullProduct")
    public PagedModel<Product> allProducts();
}
