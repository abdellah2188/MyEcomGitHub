package com.hamch.productserviceb.services.impl;


import com.hamch.productserviceb.repository.ProductRepository;
import com.hamch.productserviceb.entities.Category;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.services.ICrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.hamch.productserviceb.dto.ProductDTO;
import com.hamch.productserviceb.mapper.ProductMapper;


@Service
@Primary
public  class ProductService implements ICrudService <Product, Long>{

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Autowired
    private ProductRepository productRepository;

    private final ProductMapper productMapper;    
          
    @Override
    public void add(Product entity) {

    }

    @Override
    public void addWithFile(Product product, MultipartFile file) throws IOException {
        productRepository.save(product);
        System.out.println(file.getBytes() +"KKKJJJ"+product);
        Product p=productRepository.findById(product.getId()).get();
        //p.setPhotoName(file.getOriginalFilename());
        //MultipartFile file;

        p.setPhotoName(p.getId()+".png");
        System.out.println(file+"PPPPXXX"+p.getPhotoName());

        Files.write(Paths.get(System.getProperty("user.home")+"/ecommerce/products/"+p.getPhotoName()),  file.getBytes());
        System.out.println("POPOP"+p);
        productRepository.save(p);

    }

    @Override
    public void delete(Long id) {
        Product product = new Product();
        product.setId( id);
        productRepository.delete(product);
    }

     //@Override
    @Cacheable (value = "selectedProductsCache", key = "'selectedProducts'", unless = "#result.isEmpty()")
    public List<ProductDTO> getSelectedProducts() {
        //return productRepository.findBySelectedIsTrue();

        List<Product> selectedProducts = productRepository.findBySelectedIsTrue();
    
        return productMapper.fromListProducts(selectedProducts);
    }

    @Cacheable (value = "promotedProductsCache", key = "'promotedProducts'", unless = "#result.isEmpty()")
    public List<ProductDTO> getPromotedProducts() {
        //return productRepository.findBySelectedIsTrue();

        List<Product> promotedProducts = productRepository.findByPromotionIsTrue();
    
        return productMapper.fromListProducts(promotedProducts);
    }

    @Cacheable (value = "dispoProductsCache", key = "'dispoProducts'", unless = "#result.isEmpty()")
    public List<ProductDTO> getDispoProducts() {
        //return productRepository.findBySelectedIsTrue();

        List<Product> dispoProducts = productRepository.findByAvailableIsTrue();

        return productMapper.fromListProducts(dispoProducts);
    }

    @Cacheable (value = "productsByCategoryCache", key = "#id", unless = "#result.isEmpty()")
    public List<ProductDTO> getProductsByCategory(Long id) {
      //  List<Product> products = productRepository.findByCategory(category);
        List<Product> products = productRepository.findByCategoryId(id);

        return productMapper.fromListProducts(products);
    }
   /*
    @Override
    public List getAll() {
        return null;
    }

   



    @Override
    public void add(Payment entity) {
        paymentRepository.save(entity);
    }*/

    @Override
    public void upProduct( Product entity) {
    	
    	productRepository.save(entity);
    }
    /*
    @Override
    public void delete(Object o) {

    }

    @Override
    public void saveAll(Iterable iterable) {

    }

    @Override
    public void delete(String id) {

    }*/
}
