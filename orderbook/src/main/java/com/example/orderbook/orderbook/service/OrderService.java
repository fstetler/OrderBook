package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

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

    public List<Order> getAllOrdersByTicker(String ticker) {
        return repository.findByTickerIgnoreCase(ticker);
    }

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    // make into bigdecimal
    public Optional<Double> getMinPrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        OptionalDouble minPrice = orders.stream().mapToDouble(Order::getPrice).min();

        return Optional.of(minPrice.getAsDouble());
    }

    public Optional<Double> getMaxPrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        OptionalDouble maxPrice = orders.stream().mapToDouble(Order::getPrice).max();

        return Optional.of(maxPrice.getAsDouble());
    }

    public Optional<Double> getAveragePrice(String ticker) {
        List<Order> orders = repository.findByTickerIgnoreCase(ticker);

        if (orders.isEmpty()) {
            return Optional.empty();
        }

        OptionalDouble averagePrice = orders.stream().mapToDouble(Order::getPrice).average();

        return Optional.of(averagePrice.getAsDouble());
    }
}
