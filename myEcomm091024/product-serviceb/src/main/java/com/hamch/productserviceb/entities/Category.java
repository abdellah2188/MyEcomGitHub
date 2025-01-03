package com.hamch.productserviceb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name="category")
//@Component
public class Category implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
  //  private String photo;
 //   private String description;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private Collection<Product> products;

    public Category(Map category) {
    }
}
