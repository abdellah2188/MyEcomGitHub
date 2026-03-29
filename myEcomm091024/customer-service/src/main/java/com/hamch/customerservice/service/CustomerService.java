package com.hamch.customerservice.service;


import java.util.List;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.exceptions.EmailAlreadyExistException;

public interface CustomerService {
	
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistException;
    
    List<CustomerDTO> getAllCustomers();
    
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    
    CustomerDTO findCustomerByUsername(String username) throws CustomerNotFoundException;
    
    List<CustomerDTO> searchCustomers(String keyword);
    
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO)throws CustomerNotFoundException;
    
    void deleteCustomer(Long id)throws CustomerNotFoundException;

	//List<CustomerDTO> findByUsernameOrEmailOrMobile(String username, String email, String mobile);
    
    CustomerDTO findByUsernameOrEmailOrMobile(String username, String email, String mobile);
    

}