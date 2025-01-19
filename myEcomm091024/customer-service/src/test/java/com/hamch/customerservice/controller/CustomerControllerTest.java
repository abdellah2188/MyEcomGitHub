package com.hamch.customerservice.controller;

import com.hamch.customerservice.CustomerServiceApplication;
import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.repository.CustomerRepository;
//import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
//@DataJpaTest
//@ContextConfiguration(classes = {CustomerServiceApplication.class})
@Import(CustomerController.class)
class CustomerControllerTest {
	
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

  //  List<CustomerDTO> customers;
    static List<CustomerDTO> customers;

    @BeforeAll
    static void setUp() {
        customers = List.of(
                CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build() ,
                CustomerDTO.builder().id(2L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build(),
                CustomerDTO.builder().id(3L).firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build()
        );
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
               .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers)));
    }

	
	  @Test 
	  void shouldGetCustomerById() throws Exception { 
		  Long id = 1L;
		  Mockito.when(customerService.findCustomerById(id)).thenReturn(customers.get(0)); 
		  mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/ID/{id}",id))
		  		 .andExpect(MockMvcResultMatchers.status().isOk())
		  		 .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers.get(0)))); 
	 }
  
	  @Test 
	  void shouldNotGetCustomerByInvalidId() throws Exception { 
		  Long id = 9L;
		  Mockito.when(customerService.findCustomerById(id)).thenThrow(CustomerNotFoundException.class);
		  mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/ID/{id}",id))
		  		 .andExpect(MockMvcResultMatchers.status().isNotFound())
		  		 .andExpect(MockMvcResultMatchers.content().string("")); 
	  }
	  
	  
	  @Test 
	  void searchCustomers() throws Exception { 
		  
		  List<CustomerDTO> customersDTO = List.of(
	                CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build() ,
	                CustomerDTO.builder().id(2L).firstName("xyyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build(),
	                CustomerDTO.builder().id(3L).firstName("xzzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build()
	        );
		  
		  String keyword="x";
		  Mockito.when(customerService.searchCustomers(keyword)).thenReturn(customersDTO);
	     //   System.out.println("HHHHHHHHHHHH"+ customersDTO);

		  mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/search?keyword="+keyword)) 
		  		 .andExpect(MockMvcResultMatchers.status().isOk())
		  		 .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
		  		 .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customersDTO))); 
		  }
	 
	   
	  @Test 
	  void shouldSaveCustomer() throws Exception { 
		  CustomerDTO customerDTO= customers.get(0); 
		  String expected = """ 
		  		
		  		{ "id":1, "firstName":"xxxxx", "lastName":"xxxxx","adress":"addddrrrsssxxxx", "email":"xxxx@gmail.com", "mobile":"1111111", "userName":"XXX" }
		                    """; 
	
	  Mockito.when(customerService.saveNewCustomer(Mockito.any())).thenReturn(customers.get(0));
	  mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/add")
			  .contentType(MediaType.APPLICATION_JSON_VALUE)
			  .content(objectMapper.writeValueAsString(customerDTO)))
	          .andExpect(MockMvcResultMatchers.status().isCreated())
	  		  .andExpect(MockMvcResultMatchers.content().json(expected)); 
	  }
	  
 	  @Test 
 	  void testUpdateCustomer() throws Exception {
 		  Long customerId=1L;
 		//  CustomerDTO customerDTO= customers.get(0);
		  CustomerDTO customerDTO= CustomerDTO.builder().firstName("aaaa").lastName("aaaa").adress("addddrrrsssaaaa").email("aaaa@gmail.com").mobile("1111111").userName("AAA").build();
 		 
		  Mockito.when(customerService.updateCustomer(Mockito.eq(customerId),Mockito.any())).thenReturn(customerDTO);
 		  mockMvc.perform(MockMvcRequestBuilders.put("/api/customer/{id}", customerId)
 				  .contentType(MediaType.APPLICATION_JSON_VALUE)
 				  .content(objectMapper.writeValueAsString(customerDTO)))
 		  		  .andExpect(MockMvcResultMatchers.status().isOk())
 		  	      .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customerDTO))); 
 	  }
 	   
	  @Test 
	  void shouldDeleteCustomer() throws Exception { 
		  Long customerId=1L;
		  mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{id}",customerId))
		  		.andExpect(MockMvcResultMatchers.status().isNoContent());
	  }
	 
	  @Test 
	  void shouldGetCustomerByUserName() throws Exception { 
		  String username="XXX";
		  //System.out.println("ssssssss" + customers.get(0));

		  Mockito.when(customerService.findCustomerByUserName(username)).thenReturn(customers.get(0)); 
		  
		  mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{username}",username))
		  		 .andExpect(MockMvcResultMatchers.status().isOk())
		  		 .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers.get(0).getId()))); 
	 }
	  
      @Test 
	  void shouldGetCustomerByUsernameOrEmailOrMobile() throws Exception { 
		  String username="ZZZ";
		  String email = "yyyy@gmail.com";
		  String mobile ="1111111";
		  Mockito.when(customerService.findByUserNameOrEmailOrMobile(username, email, mobile)).thenReturn(customers); 
		  mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/or/{username}/{email}/{mobile}",username, email, mobile))
		  		 .andExpect(MockMvcResultMatchers.status().isOk())
		  		 .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers))); 
	 }
	  
}