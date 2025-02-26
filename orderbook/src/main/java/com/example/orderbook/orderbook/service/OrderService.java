package com.example.orderbook.orderbook.service;

import com.example.orderbook.orderbook.dtos.OrderMapper;
import com.example.orderbook.orderbook.dtos.requests.OrderRequest;
import com.example.orderbook.orderbook.dtos.responses.OrderResponse;
import com.example.orderbook.orderbook.enums.Tickers;
import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderBookRepository repository;

    private final OrderMapper mapper;

    public OrderService(OrderBookRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<OrderResponse> getOrderById(UUID id) {
        return Optional.of(mapper.toOrderResponse(repository.findById(id).get()));
    }

    public long numberOfOrdersByTickerAndDate(Tickers ticker, LocalDate date) {
        return repository.amountOfOrdersByTickerAndDate(ticker.toString(), date);
    }

    public OrderResponse addOrder(OrderRequest orderRequest) {
        Order order = mapper.toOrder(orderRequest);
        return mapper.toOrderResponse(repository.save(order));
    }

    public Optional<BigDecimal> getMinPrice(Tickers ticker, LocalDate date) {
        return repository.findLowestPricePerTickerAndDate(ticker.toString(), date);
    }

    public Optional<BigDecimal> getMaxPrice(Tickers ticker, LocalDate date) {
        return repository.findHighestPricePerTickerAndDate(ticker.toString(), date);
    }

    public Optional<BigDecimal> getAveragePrice(Tickers ticker, LocalDate date) {
        return repository.findAveragePricePerTickerAndDate(ticker.toString(), date);
    }

    public List<OrderResponse> getOrdersByIndex(int startIndex, int endIndex) {
        int limit = endIndex - startIndex;

        List<Order> orders = repository.findOrdersByIndex(limit, startIndex);
        return orders.stream().map(mapper::toOrderResponse).toList();
    }
}
