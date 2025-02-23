package com.hamch.orderserviceb.controller;

import com.hamch.orderserviceb.model.Product;
import lombok.Data;
import com.hamch.orderserviceb.model.Customer;

import java.util.ArrayList;
import java.util.List;
@Data
public class OrderForm {
    private Customer customer=new Customer();
    private List<OrderProduct> products=new ArrayList<>();
}
@Data
class OrderProduct{
    private Long id;
    private int quantity;
    private  int price;
    private Product product;

}
