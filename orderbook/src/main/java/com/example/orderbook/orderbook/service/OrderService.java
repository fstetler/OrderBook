package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public long numberOfOrdersByTickerAndDate(String ticker, LocalDate date) {
        return repository.amountOfOrdersByTickerAndDate(ticker, date);
    }

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    public Optional<BigDecimal> getMinPrice(String ticker, LocalDate date) {
        return repository.findLowestPricePerTickerAndDate(ticker, date);
    }

    public Optional<BigDecimal> getMaxPrice(String ticker, LocalDate date) {
        return repository.findHighestPricePerTickerAndDate(ticker, date);
    }

    public Optional<BigDecimal> getAveragePrice(String ticker, LocalDate date) {
        return repository.findAveragePricePerTickerAndDate(ticker, date);
    }
}
