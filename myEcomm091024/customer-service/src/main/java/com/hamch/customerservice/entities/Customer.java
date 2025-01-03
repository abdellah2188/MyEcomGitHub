
package com.hamch.customerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



import jakarta.persistence.*;

import java.io.Serializable;

@Entity  @NoArgsConstructor @AllArgsConstructor 
@Table(name="customer")
@Builder @ToString @Setter @Getter
public class Customer  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
  //  @Column(unique=true)
    private String email;
    private String adress;
  //  @Column(unique=true)
    private String mobile;
   // @Column(unique=true)
    private String userName;
}
