package com.hamch.productserviceb.services.impl;


import com.hamch.productserviceb.repository.ProductRepository;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.services.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Primary
public  class ProductService implements ICrudService <Product, Long>{
    @Autowired
    private ProductRepository productRepository;

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

   /*
    @Override
    public List getAll() {
        return null;
    }

    @Override
    public Page getAllPageable(Pageable pageable) {
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
