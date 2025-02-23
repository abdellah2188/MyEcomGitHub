package com.hamch.orderserviceb.model;

import com.hamch.orderserviceb.entities.Order;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Data
public class Payment {
    private Long id;
    private long cardNumber;
    private String cardType;
    private double totalAmount;
    private Date paymentDate;
    // @OneToOne(mappedBy = "payment")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //private Order order;
    @OneToOne
    private Order order;
}
