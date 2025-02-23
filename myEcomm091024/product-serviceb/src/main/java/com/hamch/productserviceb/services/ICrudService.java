package com.hamch.productserviceb.services;


import com.hamch.productserviceb.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICrudService<T, ID> {
    void add(T entity);
    void addWithFile(Product entity, MultipartFile file) throws IOException;

    void delete(ID id);
	void upProduct( Product entity);

}
