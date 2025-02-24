package com.example.orderbook.orderbook.controller;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{ticker}/lowest_price")
    public double getLowestPrice(@PathVariable String ticker) {
        return orderService.getMinPrice(ticker);
    }

    @GetMapping("/{ticker}/highest_price")
    public double getHighestPrice(@PathVariable String ticker) {
        return orderService.getMaxPrice(ticker);
    }

    @GetMapping("/{ticker}/average_price")
    public double getAveragePrice(@PathVariable String ticker) {
        return orderService.getAveragePrice(ticker);
    }
}
