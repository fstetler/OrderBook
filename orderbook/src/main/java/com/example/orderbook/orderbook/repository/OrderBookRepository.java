package com.example.orderbook.orderbook.repository;

import com.example.orderbook.orderbook.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderBookRepository extends JpaRepository<Order, UUID> {

}
