
package com.hamch.customerservice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.hamch.customerservice.CustomerServiceApplication;
import com.hamch.customerservice.entities.Customer;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

//import static org.assertj.core.api.Assertions.*; 
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @Slf4j
@Testcontainers
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CustomerServiceApplication.class})
//@Configuration
@DataJpaTest

//@ContextConfiguration
//@TestPropertySource("/application.properties")
class CustomerRepositoryWithContainerTest {

//	public String getDriverClassName() {
//		return "org.Postgres.Driver";
//	}

	 @Autowired CustomerRepository customerRepository;
	 
	 
	
	@Container
	@ServiceConnection 
	static PostgreSQLContainer database=new PostgreSQLContainer("postgres:16").withDatabaseName("customers-db").withUsername("postgres").withPassword("1234");
	  
	  	  
	@BeforeEach
	void setUp() {
		System.out.println("--------------------------jjjjj---------------------");
		database.start();
		customerRepository.save(Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build());
		customerRepository.save(Customer.builder().firstName("yyyyy").lastName("yyyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build());
		customerRepository.save(Customer.builder().firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build());
	}
	
	@After 
	public void terminate() { 
		database.stop(); 
	}

		
	  @Test 
	  public void connectionEstablishedTest() {
		  System.out.println("-----connection-----");
	  
		  assertThat(database.isCreated()).isTrue();
		  assertThat(database.isRunning()).isTrue(); 
	  }
	  
	  @Test void shoudFindByUsernameOrEmailOrMobile() { 
		  String givenEmail = "xxxx@gmail.com"; 
		  String givenUsername = "YYY"; 
		  String givenMobile ="2222222"; 
		  
		  List<Customer> expected = List.of(
				  Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(),
				  Customer.builder().firstName("yyyyy").lastName("yyyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build()
		 );
		  
		  List<Customer> result = customerRepository.findByUserNameOrEmailOrMobile(givenUsername, givenEmail, givenMobile); 
		  System.out.println("11111111111111" + result);
		  assertThat(result).isNotNull();
		  assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
		  
	  }
	  
	  @Test void shouldFindCustomersByEmail() { 
		  String givenEmail = "xxxx@gmail.com"; 
		  Customer expected = Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(); 
		  Customer result = customerRepository.findByEmail(givenEmail);
		  System.out.println("2222222222222222" + result); 
		  assertThat(result).isNotNull();
	  
		  assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
	  }
	  
	  @Test 
	  void shouldFindCustomersByUsername() { 
		  String givenUsername = "ZZZ";
		  Customer expected = Customer.builder().firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com") .mobile("33333333").userName("ZZZ").build();
		  Customer result = customerRepository.findByUserName(givenUsername);
		  System.out.println("33333333333333333" + expected);
		  System.out.println("33333333333333333" + result);

		  assertThat(result).isNotNull();
		  assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
	  }
	  
	  
	 
}
