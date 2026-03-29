package com.hamch.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@SuppressWarnings("unused")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
      private String email;
      private String adress;
      private String mobile;
      private String username;
}