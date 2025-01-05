package com.hamch.customerservice.controller;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.repository.CustomerRepository;
import com.hamch.customerservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
//@RequiredArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:4200")
public class CustomerController {
    @Autowired
    private  CustomerRepository customerRepository;
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers() {
        System.out.println("hhhhhhhhhhh");
       // return (List<Customer>) customerRepository.findAll();
        return customerService.getAllCustomers(); 
    }

    /*@GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return customerRepository.findAll();
    }*/
    @PostMapping("/add")
    //public Customer save(@RequestBody Customer customer){
    //    System.out.println("vvvvvvvvvvvvvvvv");

   //     return  customerRepository.save(customer);
   // }
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        return customerService.saveNewCustomer(customerDTO);
    }
    
    
    @GetMapping(path="/{username}")
    //@ResponseStatus(HttpStatus.OK)
    public Long getCustomerByUsername(@PathVariable("username") String username ) {
       // System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTT"+ customerRepository.findByUsername( username).getId());

        //return  customerRepository.findByUsername( username).getId();
        return customerService.findCustomerByUserName(username).getId();
    }

    @GetMapping(path="/{username}/{email}/{mobile}")
    //@ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> customerByUsernameOrEmailOrMobile(@PathVariable("username") String username, @PathVariable("email") String email, @PathVariable("mobile") String mobile ) {
        System.out.println("CCCCCCBBBBB"+ customerRepository.findByUserNameOrEmailOrMobile( username, email, mobile));

      //  return  customerRepository.findByUsernameOrEmailOrMobile(username, email, mobile);
        return customerService.findByUserNameOrEmailOrMobile(username, email, mobile);
    }
    
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String keyword){
        return customerService.searchCustomers(keyword);
    }
    
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomer(id,customerDTO);
    }
    
    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
