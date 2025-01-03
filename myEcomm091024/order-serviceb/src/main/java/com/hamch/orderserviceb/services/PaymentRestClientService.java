package com.hamch.orderserviceb.services;

import com.hamch.orderserviceb.model.Payment;
import com.hamch.orderserviceb.security.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${payment-service.url}",configuration = {ClientConfiguration.class})
public interface PaymentRestClientService {
    @GetMapping("/pauyments/{id}?projection=fullPayment")
    public Payment customerById(@PathVariable Long id);

    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Payment> allCustomers();
    @PostMapping("/api/customer/add")
    public Payment save(@RequestBody Payment customer);

    @GetMapping("/api/payment/{username}")
    Long customerByUsername(@PathVariable("username") String username);

    @GetMapping("api/payment/{username}/{email}/{mobile}")
    Payment findByUsernameoOrEmailOrMobile(@PathVariable("username") String username,
                                    @PathVariable("email") String email,
                                    @PathVariable("mobile") String mobile);
}
