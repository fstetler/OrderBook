package com.example.orderbook.orderbook.repository;

import com.example.orderbook.orderbook.enums.Tickers;
import com.example.orderbook.orderbook.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderBookRepository extends JpaRepository<Order, UUID> {

    @Query(value = "SELECT * FROM orders ORDER BY created_at DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Order> findOrdersByIndex(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(*) FROM orders o WHERE o.ticker = :ticker AND CAST(o.created_at AS DATE) = :created_at", nativeQuery = true)
    long amountOfOrdersByTickerAndDate(@Param("ticker") String ticker, @Param("created_at") LocalDate date);

    @Query(value = "SELECT MAX(o.price) FROM orders o WHERE o.ticker = :ticker AND CAST(o.created_at AS DATE) = :created_at", nativeQuery = true)
    Optional<BigDecimal> findHighestPricePerTickerAndDate(@Param("ticker") String ticker, @Param("created_at") LocalDate date);

    @Query(value = "SELECT AVG(o.price) FROM orders o WHERE o.ticker = :ticker AND CAST(o.created_at AS DATE) = :created_at", nativeQuery = true)
    Optional<BigDecimal> findAveragePricePerTickerAndDate(@Param("ticker") String ticker, @Param("created_at") LocalDate date);

    @Query(value = "SELECT MIN(o.price) FROM orders o WHERE o.ticker = :ticker AND CAST(o.created_at AS DATE) = :created_at", nativeQuery = true)
    Optional<BigDecimal> findLowestPricePerTickerAndDate(@Param("ticker") String ticker, @Param("created_at") LocalDate date);
}
