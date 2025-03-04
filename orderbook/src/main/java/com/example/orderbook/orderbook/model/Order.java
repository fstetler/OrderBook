package com.example.orderbook.orderbook.model;

import com.example.orderbook.orderbook.enums.Currencies;
import com.example.orderbook.orderbook.enums.ExchangeType;
import com.example.orderbook.orderbook.enums.Tickers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tickers ticker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExchangeType exchangetype;

    private int volume;

    @Column(precision = 20, scale = 10)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currencies currency;

    private LocalDateTime createdAt;

    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    public Order(Tickers ticker, ExchangeType exchangetype, int volume, BigDecimal price, Currencies currency) {
        this.ticker = ticker;
        this.exchangetype = exchangetype;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.createdAt = LocalDateTime.now();
    }

    public Order(Tickers ticker, ExchangeType exchangetype, int volume, BigDecimal price, Currencies currency, LocalDateTime created_at) {
        this.ticker = ticker;
        this.exchangetype = exchangetype;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.createdAt = created_at;
    }
}
