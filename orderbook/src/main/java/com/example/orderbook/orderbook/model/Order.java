package com.example.orderbook.orderbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    // make into enum
    private String type;

    private int volume;

    private BigDecimal price;

    // make into enum
    private String currency;

}
