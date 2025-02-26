package com.example.orderbook.orderbook.dtos.responses;

import com.example.orderbook.orderbook.enums.Currencies;
import com.example.orderbook.orderbook.enums.ExchangeType;
import com.example.orderbook.orderbook.enums.Tickers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class OrderResponse {

    private UUID id;
    private Tickers ticker;
    private ExchangeType exchangetype;
    private int volume;
    private BigDecimal price;
    private Currencies currency;
    private LocalDateTime createdAt;

    public OrderResponse(UUID id, Tickers ticker, ExchangeType exchangetype, int volume, BigDecimal price, Currencies currency, LocalDateTime createdAt) {
        this.id = id;
        this.ticker = ticker;
        this.exchangetype = exchangetype;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}
