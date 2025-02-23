
package com.hamch.paymentservice.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamch.paymentservice.model.Order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

//import org.springframework.data.annotation.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long cardNumber;
    private String cardType;
    private double totalAmount;
    private Date paymentDate;
    private Long orderId;
    /*@Transient
    private Order order;
*/
}
