package com.hamch.productserviceb.repository;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "productsCache", key = "'selectedProducts'", unless = "#result.isEmpty()")
    public List<Product> findBySelectedIsTrue();
    
    @Cacheable(value = "productsCache", key = "'productsByKeyword_'+#mc", unless = "#result.isEmpty()")
    @RestResource(path = "/productsByKeyword")
    public List<Product> findByNameContains(@Param("mc") String mc);
    
    @Cacheable(value = "productsCache", key = "'promoProducts'", unless = "#result.isEmpty()")
    @RestResource(path = "/promoProducts")
    public List<Product> findByPromotionIsTrue();
    
    @Cacheable(value = "productsCache", key = "'dispoProducts'", unless = "#result.isEmpty()")
    @RestResource(path = "/dispoProducts")
    public List<Product> findByAvailableIsTrue();

}
