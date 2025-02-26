package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.enums.Tickers;
import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderBookRepository repository;

    public OrderService(OrderBookRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> getOrderById(UUID id) {
        return repository.findById(id);
    }

    public long numberOfOrdersByTickerAndDate(Tickers ticker, LocalDate date) {
        return repository.amountOfOrdersByTickerAndDate(ticker.toString(), date);
    }

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    public Optional<BigDecimal> getMinPrice(Tickers ticker, LocalDate date) {
        return repository.findLowestPricePerTickerAndDate(ticker.toString(), date);
    }

    public Optional<BigDecimal> getMaxPrice(Tickers ticker, LocalDate date) {
        return repository.findHighestPricePerTickerAndDate(ticker.toString(), date);
    }

    public Optional<BigDecimal> getAveragePrice(Tickers ticker, LocalDate date) {
        return repository.findAveragePricePerTickerAndDate(ticker.toString(), date);
    }

    public List<Order> getOrdersByIndex(int startIndex, int endIndex) {
        int limit = endIndex - startIndex;
        int offset = startIndex;
        return repository.findOrdersByIndex(limit, offset);
    }
}
