package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderBookRepository repository;

    public OrderService(OrderBookRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
