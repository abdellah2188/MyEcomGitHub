package com.hamch.productserviceb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

//@Document(value = "product")

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="product")
@Data
@Builder
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
