package com.example.orderbook.orderbook.dtos.requests;

import com.example.orderbook.orderbook.enums.Currencies;
import com.example.orderbook.orderbook.enums.ExchangeType;
import com.example.orderbook.orderbook.enums.Tickers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequest {

    private Tickers ticker;
    private ExchangeType exchangetype;
    private int volume;
    private BigDecimal price;
    private Currencies currency;

    public OrderRequest(Tickers ticker, ExchangeType exchangetype, int volume, BigDecimal price, Currencies currency) {
        this.ticker = ticker;
        this.exchangetype = exchangetype;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
    }
}
