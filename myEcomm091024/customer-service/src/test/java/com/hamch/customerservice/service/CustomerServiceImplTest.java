package com.hamch.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.exceptions.EmailAlreadyExistException;
import com.hamch.customerservice.mapper.CustomerMapper;
import com.hamch.customerservice.repository.CustomerRepository;

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
    @SuppressWarnings("unused")
    void shouldSaveNewCustomer() {
        CustomerDTO customerDTO= CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").username("XXX").build();
        
        Customer customer= Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").username("XXX").build();
        
        Customer savedCustomer= Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").username("XXX").build();
        
        CustomerDTO expected= CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("sxxxx@gmail.com").mobile("1111111").username("XXX").build();
       
        
        
        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(null);
        
       // Mockito.doReturn(customerMapper.fromCustomerDTO(customerDTO)).equals(customer);
        
        Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
        
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);
        
        Mockito.when(customerMapper.fromCustomer(savedCustomer)).thenReturn(expected);
        CustomerDTO result = underTest.saveNewCustomer(customerDTO);

        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

	
	  @Test@SuppressWarnings("unused") 
 void shouldNotSaveNewCustomerWhenEmailExist() { 
		  
		  CustomerDTO customerDTO= CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("txxxx@gmail.com").mobile("1111111").username("XXX").build();
	        
	      Customer customer= Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("txxxx@gmail.com").mobile("1111111").username("XXX").build();
		  
		//  Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.of(customer));
		  Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(customer);

		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.saveNewCustomer(customerDTO)).isInstanceOf(EmailAlreadyExistException.class); 
	  }
	  
	 @Test 
    @SuppressWarnings("unused")
	 void shouldGetAllCustomers() { 
		 List<Customer> customers = List.of(
			 Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
			 Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build() , 
		     Customer.builder().firstName("wyyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build() );

		 List<CustomerDTO> expected = List.of(
			  CustomerDTO.builder().firstName("qxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
			  CustomerDTO.builder().firstName("qyyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build() );
		 
		 Mockito.when(customerRepository.findAll()).thenReturn(customers);
	       
		 Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
	    
		 List<CustomerDTO> result = underTest.getAllCustomers();
		 AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	        

	  }
	
	  @Test 
    @SuppressWarnings("unused")
	  void shouldFindCustomerById() { 
		  
		  Long customerId = 1L; 
		  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(); 
		  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build();
	  
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		  Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
		  CustomerDTO result = underTest.findCustomerById(customerId);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
    @SuppressWarnings("unused")
	  void shouldFindCustomerByUsername() { 
		  
		  String username = "XXX"; 
		  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(); 
		  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build();
	  
		  Mockito.when(customerRepository.findByUsername(username)).thenReturn(customer);
		  Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(expected);
		  CustomerDTO result = underTest.findCustomerByUsername(username);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
    @SuppressWarnings("unused")
	  void shouldFindByUsernameOrEmailOrMobile() { 
		  
		  String username = "XXX"; 
		  String email="xxxx@gmail.com";
		  String mobile ="1111111";
		  
		  
		  List<Customer> customers = List.of(
				  Customer.builder().firstName("yxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
				  Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build() ); 
				  
		  List<CustomerDTO> expected = List.of(
				 // CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
				  CustomerDTO.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY"). build() );
				 
		  
		  
		  Customer customer=Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(); 
	//	  CustomerDTO expected=CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build();
	  
		  Mockito.when(customerRepository.findByUsernameOrEmailOrMobile(username, email, mobile)).thenReturn(customer);
		  Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
		  CustomerDTO result = underTest.findByUsernameOrEmailOrMobile(username, email, mobile);
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result); 
	  }
	  
	  @Test 
    @SuppressWarnings("unused")
	  void shouldNotFindCustomerById() { 
		  Long customerId = 8L;
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.findCustomerById(customerId)).isInstanceOf(CustomerNotFoundException.class).hasMessage(null); 
	  }
		  
	  @Test 
    @SuppressWarnings("unused")
	  void shouldSearchCustomers() { 
		  String keyword="y"; 
		  List<Customer> customers = List.of(
		  Customer.builder().firstName("yxxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
		  Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build() ); 
		  
		  List<CustomerDTO> expected = List.of(
		 // CustomerDTO.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(), 
		  CustomerDTO.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY"). build() );
		 
		  Mockito.when(customerRepository.findByFirstNameContainingIgnoreCase(keyword)).thenReturn(customers);
		//  System.out.println("rrrrrr" + customerRepository.findByFirstNameContainingIgnoreCase(keyword));
		  Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected); 
		  
		  List<CustomerDTO> result = underTest.searchCustomers(keyword);
		  
		 // System.out.println("PPPPPP" + expected);
		//  System.out.println("RRRRRR" + result);
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
	  }
	  
	  @Test@SuppressWarnings("unused") 
 void updateCustomer() { 
		  Long customerId= 6L; 
		  CustomerDTO customerDTO= CustomerDTO.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build();
		 
		  Customer customer= Customer.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build(); 
		  
		  Customer updatedCustomer= Customer.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build();
		 
		  CustomerDTO expected= CustomerDTO.builder().id(6L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build();
	  
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		 
		  Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);
		 
		  Mockito.when(customerRepository.save(customer)).thenReturn(updatedCustomer);
		
		  Mockito.when(customerMapper.fromCustomer(updatedCustomer)).thenReturn(expected); 
		 
		  CustomerDTO result = underTest.updateCustomer(customerId,customerDTO);
		  
		  AssertionsForClassTypes.assertThat(result).isNotNull();
		  
		  AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
	  }
	  
	   
	  @Test
    @SuppressWarnings("unused")
	  void shouldDeleteCustomer() {
		  Long customerId =1L; 
		  Customer customer= Customer.builder().id(6L).firstName("rrrrrr").lastName("rrrrrr").adress("addddrrrsssrrrrr").email("rrrrr@gmail.com").mobile("555555555").username("RRR").build();
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer)); 
		  underTest.deleteCustomer(customerId);
		  Mockito.verify(customerRepository).deleteById(customerId); 
	  }
	  
	     
	  @Test
    @SuppressWarnings("unused")
	  void shouldNotDeleteCustomerIfNotExist() { 
		  Long customerId =9L;
		  Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
		  AssertionsForClassTypes.assertThatThrownBy(()->underTest.deleteCustomer(customerId)).isInstanceOf(CustomerNotFoundException.class); 
	  }
	 
}