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
import java.time.LocalDate;
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


    // Possibly remove this since it isnt asked for in the assignment
    @Operation(summary = "Get all orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved an order")
    })
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }


    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created an order"),
            @ApiResponse(responseCode = "400", description = "Not allowed ticker, type, or currency")
    })
    @PostMapping()
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(order));
    }


    @Operation(summary = "Get an order by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved an order"),
            @ApiResponse(responseCode = "404", description = "No order of that id was found")
    })
    @GetMapping("/orderById/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


//    @Operation(summary = "Get all orders by a specific ticker")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders from that ticker"),
//            @ApiResponse(responseCode = "404", description = "No order of that id was found")
//    })
//    @GetMapping("/order/{ticker}")
//    public List<Order> getAllOrdersByTicker(@PathVariable String ticker) {
//        return orderService.getAllOrdersByTicker(ticker);
//    }



    @Operation(summary = "Get amount of orders from a ticker on a specific date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders from that ticker at that date")
    })
    @GetMapping("/order/amount/{ticker}/{date}")
    public int getAmountOfOrdersByTicker(@PathVariable String ticker, @PathVariable LocalDate date) {
        return orderService.numberOfOrdersByTicker(ticker, date);
    }



    // change these to be from and to a specific date
    @Operation(summary = "Retrieve the lowest price of a specific ticker on a certain date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the lowest price"),
            @ApiResponse(responseCode = "404", description = "No orders found of that ticker on that date")
    })
    @GetMapping("/lowest_price/{ticker}/{date}")
    public ResponseEntity<?> getLowestPrice(@PathVariable String ticker, @PathVariable LocalDate date) {
        Optional<BigDecimal> lowestPrice = orderService.getMinPrice(ticker, date);

        if (lowestPrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No orders found of that ticker on this specific date"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(lowestPrice);
    }



    // change these to be from and to a specific date
    // change the api to seem more logical
    @Operation(summary = "Retrieve the highest price of a specific ticker on a certain date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the highest price"),
            @ApiResponse(responseCode = "404", description = "No orders found of that ticker on that date")
    })
    @GetMapping("/highest_price/{ticker}/{date}")
    public ResponseEntity<?> getHighestPrice(@PathVariable String ticker, @PathVariable LocalDate date) {
        Optional<BigDecimal> highestPrice = orderService.getMaxPrice(ticker, date);

        if (highestPrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No orders found of that ticker on this specific date"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(highestPrice);
    }



    // change these to be from and to a specific date
    @Operation(summary = "Retrieve the average price of a specific ticker on a certain date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the average price"),
            @ApiResponse(responseCode = "404", description = "No orders found of that ticker on that date")
    })
    @GetMapping("/average_price/{ticker}/{date}")
    public ResponseEntity<?> getAveragePrice(@PathVariable String ticker, @PathVariable LocalDate date) {

        Optional<BigDecimal> averagePrice = orderService.getAveragePrice(ticker, date);

        if (averagePrice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No orders found of that ticker on this specific date"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(averagePrice);
    }
}
