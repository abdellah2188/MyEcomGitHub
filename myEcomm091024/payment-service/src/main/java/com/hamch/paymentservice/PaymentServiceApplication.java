package com.hamch.paymentservice;

import com.hamch.paymentservice.entities.Payment;
import com.hamch.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentServiceApplication implements CommandLineRunner{

    @Autowired
    private PaymentRepository PaymentRepository;
   @Autowired
   private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Payment.class);


    }
}
