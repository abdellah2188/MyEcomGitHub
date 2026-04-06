package com.hamch.productserviceb.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hamch.productserviceb.entities.Category;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean promotion;
    private boolean selected;
    private boolean available;
    private String photoName;
    private long stock;
    @Transient
    private final int quantity=0;
    @ManyToOne
    @JsonBackReference
    private Category category;  
}