package com.hamch.customerservice;



import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManager;


//@ComponentScan("com.hamch.customerservice.security")
/* @Slf4j
@Profile("!test")
@EnableJpaRepositories
//@Configuration
@Configurable
@ComponentScan("com.hamch.customerservice.security")
@SpringBootApplication(scanBasePackages = "org.springframework.security.oauth2.jwt")
@ComponentScan(basePackageClasses = com.hamch.customerservice.controller.CustomerController.class)
@ComponentScan(basePackageClasses = com.hamch.customerservice.service.CustomerService.class)
@ComponentScan(basePackageClasses = com.hamch.customerservice.mapper.CustomerMapper.class) */
@SpringBootApplication
@EnableDiscoveryClient
@Configurable
@EnableJpaRepositories
@EnableCaching
//@EnableRedisRepositories
@EnableScheduling

public class CustomerServiceApplication  {
	
	public static void main(String[] args)   throws Exception {
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM000000");
		SpringApplication.run(CustomerServiceApplication.class, args);
		
	}

	/* @Bean
   RestTemplate restTemplate() {
      return new RestTemplate();
   }
 */
	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer(EntityManager entityManager) {
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM222222");
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
	  @Bean 
	  @Profile("test") 
	  CommandLineRunner  commandLineRunner(CustomerRepository customerRepository) {
		  
	      log.info("================= Initialization ================");

		  return args -> {	  
		        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQ");
			  customerRepository.save(new Customer(null,"xxxxx11","xxxxx","addddrrrsssxxxx","xxxx@gmail.com","1111111","XXX"));
			  customerRepository.save(new Customer(null,"yyyyy22","yyyyy","addddrrrsssyyyy","yyyy@gmail.com","2222222","YYY")); 
		      customerRepository.save(new Customer(null,"zzzzz33","zzzzz","addddrrrssszzzz","zzzz@gmail.com","3333333","ZZZ"));
		  //	  log.info("================= Initialization ================");
	  
	  }; 
	  }
*/	 

}
