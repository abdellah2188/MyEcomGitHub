package com.hamch.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
//@RequiredArgsConstructor
@Slf4j
//@CrossOrigin("http://localhost:4200")
//@CrossOrigin("*")
public class CustomerController {
    @Autowired
   // private  CustomerRepository customerRepository;
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers() {
       // return (List<Customer>) customerRepository.findAll();
     //   System.out.println("NNNNNNNNNNNNNNN"+ customerService.getAllCustomers());

    	return customerService.getAllCustomers(); 
    }
    
    @GetMapping("/ID/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){

        return customerService.findCustomerById(id);
    }  
    
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String keyword){

        return customerService.searchCustomers(keyword);
    }

    /*@GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return customerRepository.findAll();
    }*/
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        return customerService.saveNewCustomer(customerDTO);
    }
    
      
    
    @GetMapping(path="/{username}")
    //@ResponseStatus(HttpStatus.OK)
    public Long getCustomerByUsername(@PathVariable("username") String username ) {
        //System.out.println(username+"c"+ customerService.findCustomerByUsername(username));

        //return  customerRepository.findByUsername( username).getId();
        return customerService.findCustomerByUsername(username).getId();
    }

    @GetMapping(path="/{username}/{email}/{mobile}")
    //@ResponseStatus(HttpStatus.OK)
    //public List<CustomerDTO> getCustomerByUsernameOrEmailOrMobile(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("mobile") String mobile ) {
   // public CustomerDTO getCustomerByUsernameOrEmailOrMobile(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("mobile") String mobile ) {
    public CustomerDTO getCustomerByUsernameOrEmailOrMobile(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("mobile") String mobile ) {
  
        System.out.println("CCCCCCBBBBB"+ username+ email+ mobile);

      //  return  customerRepository.findByUsernameOrEmailOrMobile(username, email, mobile);
      CustomerDTO customerDTO = customerService.findByUsernameOrEmailOrMobile(username, email, mobile);
      if(customerDTO != null)
        return customerDTO;
      else return null;
    }
    
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomer(id,customerDTO);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}