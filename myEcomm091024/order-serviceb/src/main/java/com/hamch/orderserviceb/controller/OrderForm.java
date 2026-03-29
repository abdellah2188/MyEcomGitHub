package com.hamch.orderserviceb.controller;

import java.util.ArrayList;
import java.util.List;

import com.hamch.orderserviceb.model.Customer;
import com.hamch.orderserviceb.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@EqualsAndHashCode
@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class OrderForm {
    //@SuppressWarnings("unused")
    private  Customer customer=new Customer();
    //@SuppressWarnings("unused")
    private  List<OrderProduct> products=new ArrayList<>();

    /* public Customer getCustomer() {
        return customer;
    }

    public List<OrderProduct> getProducts() {
        return products;
    } */
}
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OrderProduct{
    private Long id;
    private int quantity;
    private  int price;
    private Product product;

    /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
 */
}
