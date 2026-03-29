package com.hamch.orderserviceb.model;

import lombok.*;

@Data
@Setter
@Getter
public class Product {
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private Integer stock;
}
