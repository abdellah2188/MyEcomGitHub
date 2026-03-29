package com.hamch.paymentservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hamch.paymentservice.entities.Payment;
import com.hamch.paymentservice.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payment")
//@RequiredArgsConstructor
@Slf4j
//@CrossOrigin("http://localhost:4200")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> findAll() {
        System.out.println("hhhhhhhhhhh");
        return (List<Payment>) paymentRepository.findAll();
    }

    /*@GetMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return paymentRepository.findAll();
    }*/
    @PostMapping()
    public Payment save(@RequestBody Payment payment){
        System.out.println("vvvvvvvvvvvvvvvv");

        return  paymentRepository.save(payment);
    }
    @GetMapping(path="/{id}")
    //@ResponseStatus(HttpStatus.OK)
    public Optional<Payment> paymentById(@PathVariable("id") Long id ) {
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTT"+ paymentRepository.findById(id));

        return  paymentRepository.findById(id);
    }
}
