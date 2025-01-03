package com.hamch.customerservice.repository;

import com.hamch.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;
import java.util.Optional;

//@CrossOrigin("*")

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   // Customer findByUsername(String username);
  //  @RestResource(path = "/name")
   Customer findByUserName(@Param(value = "username") String username);

   List<Customer> findByUserNameOrEmailOrMobile( String username, String email, String mobile);

 //  Customer findByEmail(String email);
     Customer findByEmail(String email);
   
   List<Customer> findByFirstNameContainingIgnoreCase(String keyword);

}
