
package com.hamch.customerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity  @NoArgsConstructor @AllArgsConstructor 
@Table(name="customer")
@Builder @ToString @Setter @Getter
public class Customer  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty @Size(min = 3)
    private String firstName;
    @NotEmpty @Size(min = 3)
    private String lastName;
  
    @NotEmpty @Size(min = 10)
    private String adress;
//  @Column(unique=true)
    @NotEmpty @Size(min = 7)
    private String email;
  //  @Column(unique=true)
    @NotEmpty @Size(min = 7)
    private String mobile;
   // @Column(unique=true)
    @NotEmpty @Size(min = 3)
    private String userName;
}
