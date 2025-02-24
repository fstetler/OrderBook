package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderBookRepository repository;

    public OrderService(OrderBookRepository repository) {
        this.repository = repository;
    }

    // make all this more object oriented per ticker with an interface
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(UUID id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrdersByTicker(String ticker) {
        return repository.findByTickerIgnoreCase(ticker);
    }

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    // make into bigdecimal
    public Optional<BigDecimal> getMinPrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        return orders.stream().map(Order::getPrice).min(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getMaxPrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        return orders.stream().map(Order::getPrice).max(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getAveragePrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        return orders.stream()
                .map(Order::getPrice).reduce(BigDecimal::add)
                .map(total -> total.divide(BigDecimal.valueOf(orders.size()), RoundingMode.HALF_DOWN));
    }
}
