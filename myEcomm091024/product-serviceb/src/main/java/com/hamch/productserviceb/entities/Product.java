package com.hamch.productserviceb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.annotation.Id;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

//@Document(value = "product")

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder @Table(name="product") @Getter @Setter @ToString

//@Data

//@ToString()
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
    private int quantity=0;
    @ManyToOne
 //   @ToString.Exclude
    private Category category;

}
