
package com.hamch.customerservice.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//import java.io.Serializable;

@Entity  @NoArgsConstructor @AllArgsConstructor 
@Table(name="customer")
@Builder @ToString @Setter @Getter
//@RedisHash("Customer")
public class Customer implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   //@SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    @NotEmpty @Size(min = 3)
    private String firstName;
    @NotEmpty @Size(min = 3)
    //@SuppressWarnings("unused")
    private String lastName;
  //@SuppressWarnings("unused")
    @NotEmpty @Size(min = 10)
    private String adress;
//  @Column(unique=true)
    @NotEmpty @Size(min = 7)
    //@SuppressWarnings("unused")
    private String email;
  //  @Column(unique=true)
    @NotEmpty @Size(min = 7)
    //@SuppressWarnings("unused")
    private String mobile;
   // @Column(unique=true)
   //@SuppressWarnings("unused")
    @NotEmpty @Size(min = 3)
    @Column(name="username")
    private String username;

}
