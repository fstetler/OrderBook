package com.example.orderbook.orderbook.repository;

import com.example.orderbook.orderbook.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderBookRepository extends JpaRepository<Order, UUID> {

    List<Order> findByTickerIgnoreCase(String ticker);

    @Query("SELECT MIN(o.price) FROM Order o WHERE o.ticker = :ticker")
    double findLowestPricePerTicker(String ticker);

    @Query("SELECT MAX(o.price) FROM Order o WHERE o.ticker = :ticker")
    double findHighestPricePerTicker(String ticker);

    @Query("SELECT AVG(o.price) FROM Order o WHERE o.ticker = :ticker")
    double findAveragePricePerTicker(String ticker);


}
