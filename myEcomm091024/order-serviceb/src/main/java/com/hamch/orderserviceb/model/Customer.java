package com.hamch.orderserviceb.model;


import lombok.Data;

import jakarta.persistence.*;

@Data
//@ToString @Setter @Getter @Entity  @NoArgsConstructor @AllArgsConstructor 
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String username;
}
