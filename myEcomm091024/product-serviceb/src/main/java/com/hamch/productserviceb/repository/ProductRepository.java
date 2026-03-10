package com.hamch.productserviceb.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.hamch.productserviceb.entities.Product;
//import java.util.Map;
//import java.util.Optional;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

   //Optional<Product> findById(Long id);
    @RestResource(path = "/selectedProducts")
    public List<Product> findBySelectedIsTrue();
    
    @RestResource(path = "/productsByKeyword")
    public List<Product> findByNameContains(@Param("mc") String mc);
    
    @RestResource(path = "/promoProducts")
    public List<Product> findByPromotionIsTrue();
    
    @RestResource(path = "/dispoProducts")
    public List<Product> findByAvailableIsTrue();

}
