package com.hamch.customerservice.service;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.exceptions.EmailAlreadyExistException;
import com.hamch.customerservice.mapper.CustomerMapper;
import com.hamch.customerservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistException {
        log.info(String.format("Saving new Customer => %s ", customerDTO.toString()));
        Optional<Customer> byEmail = Optional.ofNullable(customerRepository.findByEmail(customerDTO.getEmail()));
        if(byEmail.isPresent()) {
            log.error(String.format("This email %s already exist", customerDTO.getEmail()));
            throw new EmailAlreadyExistException();
        }
        Customer customerToSave = customerMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customerToSave);
        CustomerDTO result = customerMapper.fromCustomer(savedCustomer);
        return result;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return customerMapper.fromListCustomers(allCustomers);
    }

    @Override
    public CustomerDTO findCustomerByUserName(String username) throws CustomerNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByUserName(username));
        if (customer.isEmpty()) throw new CustomerNotFoundException();
        return customerMapper.fromCustomer(customer.get());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(keyword);
        System.out.println("HHHHHHHHHHHH" + customers);
        System.out.println("HHHHHHHHHHHH" + customerMapper.fromListCustomers(customers));
        return customerMapper.fromListCustomers(customers);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        customerDTO.setId(id);
        Customer customerToUpdate = customerMapper.fromCustomerDTO(customerDTO);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return customerMapper.fromCustomer(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        customerRepository.deleteById(id);
    }

	@Override
	public List<CustomerDTO> findByUserNameOrEmailOrMobile(String username, String email, String mobile) {
		
		List<Customer> customers= customerRepository.findByUserNameOrEmailOrMobile(username, email, mobile);
		return customerMapper.fromListCustomers(customers);
		
	}

	@Override
    public CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
        	log.error(String.format("This customer Id %s does not exist", id));
        	throw new CustomerNotFoundException();
        }
        return customerMapper.fromCustomer(customer.get());
    }
}