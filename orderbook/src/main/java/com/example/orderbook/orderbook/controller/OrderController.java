package com.example.orderbook.orderbook.controller;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orderById/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/order/{ticker}")
    public List<Order> getAllOrdersByTicker(@PathVariable String ticker) {
        return orderService.getAllOrdersByTicker(ticker);
    }

    @PostMapping()
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(order));
    }

    // change these to be from and to a specific date
    @GetMapping("/{ticker}/lowest_price")
    public ResponseEntity<?> getLowestPrice(@PathVariable String ticker) {
        Optional<BigDecimal> lowestPrice = orderService.getMinPrice(ticker);

        if (lowestPrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(lowestPrice);
    }

    // change these to be from and to a specific date
    // change the api to seem more logical
//    @Operation(summary = "Get the highest price of an order")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved highest price"),
//            @ApiResponse(responseCode = "204", description = "No content, no orders available"),
//            @ApiResponse(responseCode = "404", description = "No orders found for the given ticker", content = @io.swagger.v3.oas.annotations.media.Content)
//    })
    @GetMapping("/{ticker}/highest_price")
    public ResponseEntity<?> getHighestPrice(@PathVariable String ticker) {
        Optional<BigDecimal> highestPrice = orderService.getMaxPrice(ticker);

        if (highestPrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(highestPrice);
    }

    // change these to be from and to a specific date
    @GetMapping("/{ticker}/average_price")
    public ResponseEntity<?> getAveragePrice(@PathVariable String ticker) {
        Optional<BigDecimal> averagePrice = orderService.getAveragePrice(ticker);

        if (averagePrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No orders found of that ticker"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(averagePrice);
    }
}
