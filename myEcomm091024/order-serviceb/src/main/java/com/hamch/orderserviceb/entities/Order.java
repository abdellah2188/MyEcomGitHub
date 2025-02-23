package com.hamch.orderserviceb.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamch.orderserviceb.model.Customer;
import lombok.*;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Date;


//@Table(name = "t_orders")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@ToString
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems;
    //@Column(name = "customerId")
    private Long customerId;
    @Transient
    private Customer customer;
    private double totalAmount;
    //@OneToOne
    //private Payment payment;


    /*@OneToOne(mappedBy = "order")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Payment payment;
*/
}
