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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Document(value = "product")

@ToString
@Setter
@EqualsAndHashCode
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="product")
@Data
@Builder
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
    @ManyToOne
    private Category category;

       
}
