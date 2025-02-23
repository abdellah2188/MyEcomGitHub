package com.hamch.productserviceb.services.impl;


import com.hamch.productserviceb.repository.CategoryRepository;
import com.hamch.productserviceb.entities.Category;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.services.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
public  class CategoryService implements ICrudService <Category, Long>{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void add(Category category) {
         categoryRepository.save(category);
    }

    @Override
    public void addWithFile(Product entity, MultipartFile file) throws IOException {

    }

    @Override
    public void delete(Long id) {

    }

	@Override
	public void upProduct( Product entity) {
		// TODO Auto-generated method stub
		
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

    /*@Override
    public void update(Object entity) {

    }

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
