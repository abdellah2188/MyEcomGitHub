package com.hamch.productserviceb.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name="category")
//@Component
public class Category implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    private String name;
  //  private String photo;
 //   private String description;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    @SuppressWarnings("unused")
    private Collection<Product> products;
    @SuppressWarnings("unused")
    public Category(Map<?, ?> category) {
    }
}
