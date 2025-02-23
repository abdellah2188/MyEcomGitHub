package com.hamch.customerservice.service;


import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.exceptions.EmailAlreadyExistException;


import java.util.List;

public interface CustomerService {
	
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistException;
    
    List<CustomerDTO> getAllCustomers();
    
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    
    CustomerDTO findCustomerByUserName(String username) throws CustomerNotFoundException;
    
    List<CustomerDTO> searchCustomers(String keyword);
    
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO)throws CustomerNotFoundException;
    
    void deleteCustomer(Long id)throws CustomerNotFoundException;

	List<CustomerDTO> findByUserNameOrEmailOrMobile(String username, String email, String mobile);
    
    
}