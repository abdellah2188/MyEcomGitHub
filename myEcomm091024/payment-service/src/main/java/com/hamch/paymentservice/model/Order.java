package com.hamch.paymentservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Long customerId;
    private double totalAmount;
    private Long orderId;

}
