package com.hamch.orderserviceb.services;

import com.hamch.orderserviceb.model.Customer;
import com.hamch.orderserviceb.security.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "customer-service", url = "${customer-service.url}",configuration = {ClientConfiguration.class})
public interface CustomerRestClientService {
    @GetMapping("/customers/{id}?projection=fullCustomer")
    public Customer customerById(@PathVariable Long id);

    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Customer> allCustomers();
    @PostMapping("/api/customer/add")
    public Customer save(@RequestBody Customer customer);

    @GetMapping("/api/customer/{username}")
    Long customerByUsername(@PathVariable("username") String username);

    @GetMapping("api/customer/{username}/{email}/{mobile}")
    Customer findByUsernameoOrEmailOrMobile(@PathVariable("username") String username,
                                    @PathVariable("email") String email,
                                    @PathVariable("mobile") String mobile);
}
