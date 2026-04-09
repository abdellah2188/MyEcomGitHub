package com.hamch.productserviceb.dto;


import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hamch.productserviceb.entities.Product;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class CategoryDTO {
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    @JsonManagedReference
    private Collection<Product> products;
}
