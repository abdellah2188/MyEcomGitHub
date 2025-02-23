package com.hamch.paymentservice.repository;

import com.hamch.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

//@CrossOrigin("*")
@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
   // Payment findByUsername(String username);
  //  @RestResource(path = "/name")
   Optional<Payment> findById(@Param(value = "id") Long id);

}
