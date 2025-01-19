
package com.hamch.customerservice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.assertj.core.api.Assertions.*; 
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @Slf4j
@Testcontainers
//@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CustomerServiceApplication.class})
//@Configuration
@DataJpaTest

//@ContextConfiguration
//@TestPropertySource("/application-test.properties")
class CustomerRepositoryWithContainerTest {

//	public String getDriverClassName() {
//		return "org.Postgres.Driver";
//	}

	 @Autowired CustomerRepository customerRepository2;
	 
	 static List<CustomerDTO> customers;
	
	@Container
	@ServiceConnection 
	static PostgreSQLContainer database=new PostgreSQLContainer("postgres:16"); //.withDatabaseName("customers-db").withUsername("postgres").withPassword("1234");
	  
	  	  
	 @BeforeAll
	    static void setup(@Autowired CustomerRepository customerRepository) {
	        //System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGG");

	    	database.start();
	    	
	    	 customerRepository.save(new Customer(null,"xxxxx11","xxxxx","addddrrrsssxxxx","xxxx@gmail.com","1111111","XXX"));
	         customerRepository.save(new Customer(null,"yyyyy22","yyyyy","addddrrrsssyyyy","yyyy@gmail.com","2222222","YYY")); 
	         customerRepository.save(new Customer(null,"zzzzz33","zzzzz","addddrrrssszzzz","zzzz@gmail.com","3333333","ZZZ"));
	    
	         customers = new ArrayList<>();
	         //System.out.println("aaaaaaaaaaaaaaaaaa"+ customers);

	         customers.add(CustomerDTO.builder().id(1L).firstName("xxxxx11").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build());
	         customers.add(CustomerDTO.builder().id(2L).firstName("yyyyy22").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build());
	         customers.add(CustomerDTO.builder().id(3L).firstName("zzzzz33").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("3333333").userName("ZZZ").build());
	         //System.out.println("bbbbbbbbbbbbbbbbb"+ customers);
	         
	    }
	    
	@AfterAll 
	static void terminate() { 
		database.stop(); 
	}

		
	  @Test 
	  public void connectionEstablishedTest() {
		  System.out.println("-----connection-----");
	  
	//	  assertThat(database.isCreated()).isTrue();
	//	  assertThat(database.isRunning()).isTrue(); 
	  }
	  
	  @Test void shoudFindByUsernameOrEmailOrMobile() { 
		  String givenEmail = "xxxx@gmail.com"; 
		  String givenUsername = "YYY"; 
		  String givenMobile ="3333333"; 
		  
		 		  
		  List<Customer> result = customerRepository2.findByUserNameOrEmailOrMobile(givenUsername, givenEmail, givenMobile); 
		//  System.out.println("11111111111111" + result);
		  assertThat(result).isNotNull();
		  assertThat(customers).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
		  
	  }
	  
	  @Test void shouldFindCustomersByEmail() { 
		  String givenEmail = "xxxx@gmail.com"; 
		 
		  Customer expected = Customer.builder().firstName("xxxxx11").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(); 
		  Customer result = customerRepository2.findByEmail(givenEmail);
		//  System.out.println("2222222222222222" + result); 
		  assertThat(result).isNotNull();
	  
		  assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
	  }
	  
	  @Test 
	  void shouldFindCustomersByUsername() { 
		  String givenUsername = "ZZZ";
		  Customer expected = Customer.builder().firstName("zzzzz33").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com") .mobile("3333333").userName("ZZZ").build();
		  Customer result = customerRepository2.findByUserName(givenUsername);
	//	  System.out.println("33333333333333333" + expected);
	//	  System.out.println("33333333333333333" + result);

		  assertThat(result).isNotNull();
		  assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result); 
	  }
	  
	  
	 
}
