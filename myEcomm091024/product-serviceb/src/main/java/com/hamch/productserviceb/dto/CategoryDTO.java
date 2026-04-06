package com.hamch.productserviceb.dto;


import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hamch.productserviceb.entities.Product;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryDTO {
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    @JsonManagedReference
    private Collection<Product> products;
}
