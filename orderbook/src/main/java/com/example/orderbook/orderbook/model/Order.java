package com.example.orderbook.orderbook.model;

import com.example.orderbook.orderbook.utils.Currencies;
import com.example.orderbook.orderbook.utils.ExchangeType;
import com.example.orderbook.orderbook.utils.Tickers;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private UUID id;

    // make into enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tickers ticker;

    // make into enum

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExchangeType exchangetype;

    private int volume;

    // make into bigdecimal
    @Column(precision = 20, scale = 10)
    private BigDecimal price;

    // make into enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currencies currency;

    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(UUID id, Tickers ticker, ExchangeType exchangetype, int volume, BigDecimal price, Currencies currency, LocalDateTime created_at) {
        this.id = id;
        this.ticker = ticker;
        this.exchangetype = exchangetype;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.createdAt = created_at;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tickers getTicker() {
        return ticker;
    }

    public void setTicker(Tickers ticker) {
        this.ticker = ticker;
    }

    public ExchangeType getExchangetype() {
        return exchangetype;
    }

    public void setExchangetype(ExchangeType exchangetype) {
        this.exchangetype = exchangetype;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
