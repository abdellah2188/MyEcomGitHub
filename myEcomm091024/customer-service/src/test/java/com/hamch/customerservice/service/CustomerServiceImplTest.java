package com.hamch.customerservice.service;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.exceptions.EmailAlreadyExistException;
import com.hamch.customerservice.mapper.CustomerMapper;
import com.hamch.customerservice.repository.CustomerRepository;

import jakarta.validation.constraints.Null;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
   
   // @InjectMocks
    @Mock
    public CustomerMapper customerMapper;
    
    
    @InjectMocks
    private CustomerServiceImpl underTest;
    @Test
    void shouldSaveNewCustomer() {
        CustomerDTO customerDTO= CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").userName("XXX").build();
        
        Customer customer= Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").userName("XXX").build();
        
        Customer savedCustomer= Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").userName("XXX").build();
        
        CustomerDTO expected= CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").userName("XXX").build();
       
        
        
        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(null);
        
       // Mockito.doReturn(customerMapper.fromCustomerDTO(customerDTO)).equals(customer);
        
        Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
        
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);
        
        Mockito.when(customerMapper.fromCustomer(savedCustomer)).thenReturn(expected);
        CustomerDTO result = underTest.saveNewCustomer(customerDTO);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

	
	  @Test void shouldNotSaveNewCustomerWhenEmailExist() { 
		  
		  CustomerDTO customerDTO= CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("txxxx@gmail.com").mobile("1111111").userName("XXX").build();
	        
	      Customer customer= Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("txxxx@gmail.com").mobile("1111111").userName("XXX").build();
		  
		//  Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.of(customer));
		  Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(customer);

		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.saveNewCustomer(customerDTO)).isInstanceOf(EmailAlreadyExistException.class); 
	  }
	  
	 @Test 
	 void shouldGetAllCustomers() { 
		 List<Customer> customers = List.of(
			 Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
			 Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build() , 
		     Customer.builder().firstName("wyyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build() );

		 List<CustomerDTO> expected = List.of(
			  CustomerDTO.builder().firstName("qxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
			  CustomerDTO.builder().firstName("qyyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build() );
		 
		 Mockito.when(customerRepository.findAll()).thenReturn(customers);
	       
		 Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
	    
		 List<CustomerDTO> result = underTest.getAllCustomers();
		 AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	        

	  }
	
	  @Test 
	  void shouldFindCustomerById() { 
		  
		  Long customerId = 1L; 
		  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(); 
		  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
	  
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		  Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
		  CustomerDTO result = underTest.findCustomerById(customerId);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
	  void shouldFindCustomerByUserName() { 
		  
		  String username = "XXX"; 
		  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(); 
		  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
	  
		  Mockito.when(customerRepository.findByUserName(username)).thenReturn(customer);
		  Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
		  CustomerDTO result = underTest.findCustomerByUserName(username);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
	  void shouldFindByUserNameOrEmailOrMobile() { 
		  
		  String username = "XXX"; 
		  String email="xxxx@gmail.com";
		  String mobile ="1111111";
		  
		  
		  List<Customer> customers = List.of(
				  Customer.builder().firstName("yxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
				  Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build() ); 
				  
		  List<CustomerDTO> expected = List.of(
				 // CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
				  CustomerDTO.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY"). build() );
				 
		  
		  
	//	  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(); 
	//	  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
	  
		  Mockito.when(customerRepository.findByUserNameOrEmailOrMobile(username, email, mobile)).thenReturn(customers);
		  Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
		  List<CustomerDTO> result = underTest.findByUserNameOrEmailOrMobile(username, email, mobile);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
	  void shouldNotFindCustomerById() { 
		  Long customerId = 8L;
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.findCustomerById(customerId)).isInstanceOf(CustomerNotFoundException.class).hasMessage(null); 
	  }
		  
	  @Test 
	  void shouldSearchCustomers() { 
		  String keyword="y"; 
		  List<Customer> customers = List.of(
		  Customer.builder().firstName("yxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
		  Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build() ); 
		  
		  List<CustomerDTO> expected = List.of(
		 // CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(), 
		  CustomerDTO.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY"). build() );
		 
		  Mockito.when(customerRepository.findByFirstNameContainingIgnoreCase(keyword)).thenReturn(customers);
		  System.out.println("rrrrrr" + customerRepository.findByFirstNameContainingIgnoreCase(keyword));
		  Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected); 
		  
		  List<CustomerDTO> result = underTest.searchCustomers(keyword);
		  
		  System.out.println("PPPPPP" + expected);
		  System.out.println("RRRRRR" + result);
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
	  }
	  
	  @Test void updateCustomer() { 
		  Long customerId= 6L; 
		  CustomerDTO customerDTO= CustomerDTO.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build();
		 
		  Customer customer= Customer.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build(); 
		  
		  Customer updatedCustomer= Customer.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build();
		 
		  CustomerDTO expected= CustomerDTO.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build();
	  
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		 
		  Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
		 
		  Mockito.when(customerRepository.save(customer)).thenReturn(updatedCustomer);
		
		  Mockito.when(customerMapper.fromCustomer(updatedCustomer)).thenReturn(expected); 
		 
		  CustomerDTO result = underTest.updateCustomer(customerId,customerDTO);
		  
		  AssertionsForClassTypes.assertThat(result).isNotNull();
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
	  }
	  
	   
	  @Test
	  void shouldDeleteCustomer() {
		  Long customerId =1L; 
		  Customer customer= Customer.builder().id(6L).firstName("rrrrrr").lastName("rrrrrr").adress("addddrrrsssrrrrr").email("rrrrr@gmail.com").mobile("555555555").userName("RRR").build();
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer)); 
		  underTest.deleteCustomer(customerId);
		  Mockito.verify(customerRepository).deleteById(customerId); 
	  }
	  
	     
	  @Test
	  void shouldNotDeleteCustomerIfNotExist() { 
		  Long customerId =9L;
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.deleteCustomer(customerId)).isInstanceOf(CustomerNotFoundException.class); 
	  }
	 
}