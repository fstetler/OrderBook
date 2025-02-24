package com.example.orderbook.orderbook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private UUID id;

    private String ticker;

    // make into enum
    private String type;

    private int volume;

    // make into bigdecimal
    private double price;

    // make into enum
    private String currency;

    private LocalDateTime dateTime;

    public Order() {
    }

    public Order(UUID id, String ticker, String type, int volume, double price, String currency, LocalDateTime date_time) {
        this.id = id;
        this.ticker = ticker;
        this.type = type;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.dateTime = date_time;
    }

    @PrePersist
    private void onCreate() {
        this.dateTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
