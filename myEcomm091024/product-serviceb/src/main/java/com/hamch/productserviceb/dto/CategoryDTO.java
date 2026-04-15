package com.hamch.productserviceb.dto;


import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hamch.productserviceb.entities.Product;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    //@JsonManagedReference
    @JsonIgnoreProperties("category") 
    private Collection<Product> products;
}
