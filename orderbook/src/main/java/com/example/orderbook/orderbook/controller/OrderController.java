package com.example.orderbook.orderbook.controller;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
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
}
