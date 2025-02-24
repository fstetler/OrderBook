package com.example.orderbook.orderbook.repository;

import com.example.orderbook.orderbook.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderBookRepository extends JpaRepository<Order, UUID> {

    List<Order> findByTickerIgnoreCase(String ticker);

    // this could be done on the service level with findAll and filter on date
    List<Order> findByCreatedAt(LocalDateTime date);

    // this could also be done on the service layer with findall and filter per ticker and then find lowest
    @Query("SELECT MIN(o.price) FROM Order o WHERE o.ticker = :ticker")
    BigDecimal findLowestPricePerTicker(String ticker);

    @Query("SELECT MAX(o.price) FROM Order o WHERE o.ticker = :ticker")
    BigDecimal findHighestPricePerTicker(String ticker);

    @Query("SELECT AVG(o.price) FROM Order o WHERE o.ticker = :ticker")
    BigDecimal findAveragePricePerTicker(String ticker);


}
