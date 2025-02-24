package com.example.orderbook.orderbook.controller;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/getAllOrders/{ticker}")
    public List<Order> getAllOrdersByTicker(@PathVariable String ticker) {
        return orderService.getAllOrdersByTicker(ticker);
    }

    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(order));
    }

    // change these to be from and to a specific date
    @GetMapping("/{ticker}/lowest_price")
    public ResponseEntity<?> getLowestPrice(@PathVariable String ticker) {
        Optional<BigDecimal> price = orderService.getMinPrice(ticker);

        if (price.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMinPrice(ticker));
    }

    // change these to be from and to a specific date
    @GetMapping("/{ticker}/highest_price")
    public ResponseEntity<?> getHighestPrice(@PathVariable String ticker) {
        Optional<BigDecimal> price = orderService.getMaxPrice(ticker);

        if (price.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMaxPrice(ticker));
    }

    // change these to be from and to a specific date
    @GetMapping("/{ticker}/average_price")
    public ResponseEntity<?> getAveragePrice(@PathVariable String ticker) {
        Optional<BigDecimal> price = orderService.getAveragePrice(ticker);

        if (price.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAveragePrice(ticker));
    }
}
