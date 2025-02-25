package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(UUID id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrdersByTicker(String ticker) {
        return repository.findAll().stream().filter(o -> o.getTicker().name().equalsIgnoreCase(ticker)).toList();
    }

    public int numberOfOrdersByTicker(String ticker, LocalDate date) {
        return repository.findAll().stream()
                .filter(o -> o.getTicker().name().equalsIgnoreCase(ticker))
                .filter(o -> o.getCreatedAt().toLocalDate().equals(date))
                .toList().size();
    }

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    // make into bigdecimal
    public Optional<BigDecimal> getMinPrice(String ticker, LocalDate date) {
        List<Order> ordersByTickerAndDate = getOrders(ticker, date);

        if (ordersByTickerAndDate.isEmpty()) {
            return Optional.empty();
        }

        return ordersByTickerAndDate.stream().map(Order::getPrice).min(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getMaxPrice(String ticker, LocalDate date) {
        List<Order> ordersByTickerAndDate = getOrders(ticker, date);

        if (ordersByTickerAndDate.isEmpty()) {
            return Optional.empty();
        }

        return ordersByTickerAndDate.stream().map(Order::getPrice).max(BigDecimal::compareTo);
    }

    // for tomorrow
    // fix date on all max/min/avg


    public Optional<BigDecimal> getAveragePrice(String ticker, LocalDate date) {
        List<Order> ordersByTickerAndDate = getOrders(ticker, date);

        if (ordersByTickerAndDate.isEmpty()) {
            return Optional.empty();
        }

        return ordersByTickerAndDate.stream()
                .map(Order::getPrice).reduce(BigDecimal::add)
                .map(total -> total.divide(BigDecimal.valueOf(ordersByTickerAndDate.size()), RoundingMode.HALF_DOWN));
    }

    private List<Order> getOrders(String ticker, LocalDate date) {
        return repository.findAll().stream()
                .filter(o -> o.getTicker().toString().equalsIgnoreCase(ticker))
                .filter(o -> o.getCreatedAt().toLocalDate().equals(date)).toList();
    }
}
