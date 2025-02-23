package com.hamch.productserviceb.repository;


import com.hamch.productserviceb.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.mongodb.repository.MongoRepository;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
