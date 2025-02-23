package com.hamch.orderserviceb.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    private String name;
    private String email;
    private String adress;
    private String mobile;
    private String username;
}
