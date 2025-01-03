package com.hamch.customerservice;

//import javax.persistence.EntityManager;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

//import com.hamch.customerservice.repository.CustomerRepository;

import jakarta.persistence.*;
//import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
//@Slf4j
@Profile("!test")
@EnableJpaRepositories
@Configurable
public class CustomerServiceApplication  {

	//@Autowired
	//private CustomerRepository customerRepository;
//	@Autowired
//	private RepositoryRestConfiguration repositoryRestConfiguration;

	
	public static void main(String[] args)   throws Exception {
		SpringApplication.run(CustomerServiceApplication.class, args);
		
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer(EntityManagerFactory entityManager) {
		return RepositoryRestConfigurer.withConfig(config -> {
			config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(entityType -> entityType.getJavaType()).toArray(Class[]::new));
		});

	}

	/*
	 * @Override public void run(String... args) throws Exception { //
	 * repositoryRestConfiguration.exposeIdsFor(Customer.class);
	 * 
	 * 
	 * customerRepository.save(new
	 * Customer(null,"customerxxxxx","customerxxxx@mail.com","adressxxxx",
	 * "1111111111","custx")); customerRepository.save(new
	 * Customer(null,"customer 2","customer-2@mail.com","adress222222","2222222222",
	 * "cust2")); customerRepository.save(new
	 * Customer(null,"customer 3","customer-3@mail.com","adress333333","3333333333",
	 * "cust3"));
	 * 
	 * 
	 * }
	 */

	/*
	 * @Bean //@Profile("!test") CommandLineRunner
	 * commandLineRunner(CustomerRepository customerRepository) { return args -> {
	 * customerRepository.save( new Customer(null, "customerxxxxx",
	 * "customerxxxx@mail.com", "adressxxxx", "1111111111", "custx")); //
	 * log.info("================= Initialization ================");
	 * 
	 * }; }
	 */

}
