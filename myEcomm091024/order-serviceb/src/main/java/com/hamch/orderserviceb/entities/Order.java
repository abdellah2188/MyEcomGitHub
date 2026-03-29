package com.hamch.orderserviceb.entities;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
