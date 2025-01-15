package com.hamch.customerservice.controller;

import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.repository.CustomerRepository;
import com.hamch.customerservice.dto.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.RETURNS_DEFAULTS;

import java.util.*;
import java.util.stream.Collectors;

@Testcontainers
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)

@Transactional
public class CustomerIntegrationTest {
	
		
	@Autowired
	CustomerRepository customerRepository;
	
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgreSQLContainer=new PostgreSQLContainer("postgres:16");

    static List<CustomerDTO> customers;
    
   /* @Before(value = "")
     void setup(){
        System.out.println("JJJJJJJJJJJJJJ");

    	postgreSQLContainer.start();
    	
    	
    }	*/
    
    
    @BeforeAll
    static void setup(@Autowired CustomerRepository customerRepository) {
        //System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGG");

    	postgreSQLContainer.start();
    	
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
    	postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
       
    }

    @Test
    @Rollback
    void shouldGetAllCustomers(){
        ResponseEntity<CustomerDTO[]> response = testRestTemplate.exchange("/api/customer", HttpMethod.GET, null, CustomerDTO[].class);
        List<CustomerDTO> content = Arrays.asList(response.getBody());
       // System.out.println("SSSSSSSSSSSSSSSSs"+ content);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(3);
        AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(customers);
    }
    
    @Test
    @Rollback
    void shouldSearchCustomersByFirstName(){
        String keyword="x";
        ResponseEntity<CustomerDTO[]> response = testRestTemplate.exchange("/api/customer/search?keyword="+keyword, HttpMethod.GET, null, CustomerDTO[].class);
        List<CustomerDTO> content = Arrays.asList(response.getBody());
     //   System.out.println("EEEEEEEEEEEEEEEEEEe"+ content);

        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(1);
        List<CustomerDTO> expected = customers.stream().filter(c -> c.getFirstName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(expected);
    }
    
    @Test
    void shouldGetCustomerById(){
        Long customerId = 1L;
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange("/api/customer/ID/"+customerId, HttpMethod.GET, null, CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).isNotNull();
        AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(customers.get(0));
    }
    
    @Test
    void shouldNotFindCustomerById(){
        Long customerId = 9L;
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange("/api/customer/ID/"+customerId, HttpMethod.GET, null, CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    
    @Test
    //@Rollback
    void shouldSaveValidCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("sssss").lastName("sssss").adress("addddssssssss").email("ssss@gmail.com").mobile("66666666").userName("SSS").build();
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange("/api/customer/add", HttpMethod.POST, new HttpEntity<>(customerDTO), CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(customerDTO);
    }

    
    @Test
   // @Rollback
    void shouldNotSaveInValidCustomer() throws JsonProcessingException {
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("").lastName("").adress("").email("").mobile("").userName("").build();
        ResponseEntity<String> response = testRestTemplate.exchange("/api/customer/add", HttpMethod.POST, new HttpEntity<>(customerDTO), String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, ArrayList<String>> errors = objectMapper.readValue(response.getBody(), HashMap.class);
        Assertions.assertThat(errors.keySet().size()).isEqualTo(6);
        Assertions.assertThat(errors.get("firstName").size()).isEqualTo(2);
        Assertions.assertThat(errors.get("lastName").size()).isEqualTo(2);
        Assertions.assertThat(errors.get("email").size()).isEqualTo(2);
        Assertions.assertThat(errors.get("adress").size()).isEqualTo(2);
        Assertions.assertThat(errors.get("mobile").size()).isEqualTo(2);
        Assertions.assertThat(errors.get("userName").size()).isEqualTo(2);
    }

    @Test
    //@Rollback
    void shouldUpdateValidCustomer(){
        Long customerId = 2L;
        CustomerDTO customerDTO = CustomerDTO.builder().firstName("sssss").lastName("sssss").adress("addddssssssss").email("ssss@gmail.com").mobile("66666666").userName("SSS").build();
        ResponseEntity<CustomerDTO> response = testRestTemplate.exchange("/api/customer/"+customerId, HttpMethod.PUT, new HttpEntity<>(customerDTO), CustomerDTO.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(customerDTO);
    }
    
    
    @Test
    //@Rollback
    void shouldDeleteCustomer(){
        Long customerId = 3L;
        ResponseEntity<String> response = testRestTemplate.exchange("/api/customer/"+customerId, HttpMethod.DELETE, null, String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}