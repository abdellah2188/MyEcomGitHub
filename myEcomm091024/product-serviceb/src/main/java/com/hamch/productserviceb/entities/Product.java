package com.hamch.productserviceb.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonBackReference;

//@Document(value = "product")

@Data @Builder@ToString @Setter @Getter @Entity @AllArgsConstructor @NoArgsConstructor
@Table(name="product")

public class Product implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "`id`")
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
    @JsonBackReference
    @ManyToOne
    private Category category;
    public Product orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }

       
}
