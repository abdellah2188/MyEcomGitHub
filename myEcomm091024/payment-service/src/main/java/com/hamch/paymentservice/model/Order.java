package com.hamch.paymentservice.model;

import lombok.*;

import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;

@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Long customerId;
    private double totalAmount;
    private Long orderId;

}
