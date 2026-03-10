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
//@ToString()
public class Product implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "`id`")
   @SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    private String name;
    @SuppressWarnings("unused")
    private String description;
    @SuppressWarnings("unused")
    private double price;
    @SuppressWarnings("unused")
    private boolean promotion;
    @SuppressWarnings("unused")
    private boolean selected;
    @SuppressWarnings("unused")
    private boolean available;
    @SuppressWarnings("unused")
    private String photoName;
    @SuppressWarnings("unused")
    private long stock;
    @Transient
    @SuppressWarnings("unused")
    private final int quantity=0;
    @ManyToOne
 //   @ToString.Exclude
    @SuppressWarnings("unused")
    private Category category;

}
