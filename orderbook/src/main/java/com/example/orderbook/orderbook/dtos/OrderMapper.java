package com.example.orderbook.orderbook.dtos;

import com.example.orderbook.orderbook.dtos.requests.OrderRequest;
import com.example.orderbook.orderbook.dtos.responses.OrderResponse;
import com.example.orderbook.orderbook.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setTicker(orderRequest.getTicker());
        order.setExchangetype(orderRequest.getExchangetype());
        order.setVolume(orderRequest.getVolume());
        order.setPrice(orderRequest.getPrice());
        order.setCurrency(orderRequest.getCurrency());
        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setTicker(order.getTicker());
        orderResponse.setExchangetype(order.getExchangetype());
        orderResponse.setVolume(order.getVolume());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setCurrency(order.getCurrency());
        orderResponse.setCreatedAt(order.getCreatedAt());
        return orderResponse;
    }
}
