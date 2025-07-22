package com.hamch.orderserviceb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

//@Data
@ToString @Setter @Getter @Entity  @NoArgsConstructor @AllArgsConstructor 
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String username;
}
