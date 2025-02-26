package com.example.orderbook.orderbook;

import com.example.orderbook.orderbook.enums.Currencies;
import com.example.orderbook.orderbook.enums.ExchangeType;
import com.example.orderbook.orderbook.enums.Tickers;
import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrderbookApplicationTests {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Test
    public void createNewOrderAndSave() {
        Order order = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD);

        Order orderSave = orderBookRepository.save(order);

        Assertions.assertNotNull(orderSave.getId());
        Assertions.assertEquals(orderSave.getTicker(), Tickers.TSLA);

    }

    @Test
    public void findAllOrdersBetweenTwoIndexes() {

        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.BUY, 100, BigDecimal.valueOf(20.7515), Currencies.USD, LocalDateTime.of(2025, 5, 5, 15, 0, 0)));
        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(15.1515), Currencies.USD, LocalDateTime.of(2025, 5, 4, 14, 0, 0)));
        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.SELL, 300, BigDecimal.valueOf(150.5351), Currencies.SEK, LocalDateTime.of(2025, 5, 3, 13, 0, 0)));
        orderBookRepository.save(new Order(Tickers.GME, ExchangeType.BUY, 400, BigDecimal.valueOf(250.7586), Currencies.USD, LocalDateTime.of(2025, 5, 2, 12, 0, 0)));
        orderBookRepository.save(new Order(Tickers.SAVE, ExchangeType.BUY, 500, BigDecimal.valueOf(10.5386), Currencies.USD, LocalDateTime.of(2025, 5, 1, 11, 0, 0)));

        int limit = 3;
        int offset = 1;
        List<Order> orders = orderBookRepository.findOrdersByIndex(limit, offset);
        Assertions.assertEquals(3, orders.size());
        Assertions.assertEquals(500, orders.get(0).getVolume());
    }

    @Test
    public void findOrderById() {

        Order order = orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD));
        UUID id = order.getId();

        Assertions.assertEquals(orderBookRepository.findById(id).get().getId(), id);
    }

    @Test
    public void findHighestValueForOrderForATickerAndDate() {
        Order order1 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD);
        Order order2 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(15.7515), Currencies.USD);
        Order order3 = new Order(Tickers.GME, ExchangeType.BUY, 500, BigDecimal.valueOf(10.7515), Currencies.USD);

        orderBookRepository.save(order1);
        orderBookRepository.save(order2);
        orderBookRepository.save(order3);

        BigDecimal highestPrice = orderBookRepository.findHighestPricePerTickerAndDate(Tickers.TSLA.toString(), LocalDate.now()).get();

        Assertions.assertEquals(BigDecimal.valueOf(20.7515), highestPrice.stripTrailingZeros());
    }

    @Test
    public void findLowestValueForOrderForATickerAndDate() {
        Order order1 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD);
        Order order2 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(15.7515), Currencies.USD);
        Order order3 = new Order(Tickers.GME, ExchangeType.BUY, 500, BigDecimal.valueOf(10.7515), Currencies.USD);

        orderBookRepository.save(order1);
        orderBookRepository.save(order2);
        orderBookRepository.save(order3);

        BigDecimal highestPrice = orderBookRepository.findLowestPricePerTickerAndDate(Tickers.TSLA.toString(), LocalDate.now()).get();

        Assertions.assertEquals(BigDecimal.valueOf(15.7515), highestPrice.stripTrailingZeros());

    }

    @Test
    public void findAverageValueForOrderForATickerAndDate() {
        Order order1 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD);
        Order order2 = new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(15.7515), Currencies.USD);
        Order order3 = new Order(Tickers.GME, ExchangeType.BUY, 500, BigDecimal.valueOf(10.7515), Currencies.USD);

        orderBookRepository.save(order1);
        orderBookRepository.save(order2);
        orderBookRepository.save(order3);

        BigDecimal highestPrice = orderBookRepository.findAveragePricePerTickerAndDate(Tickers.TSLA.toString(), LocalDate.now()).get();

        Assertions.assertEquals(BigDecimal.valueOf(18.2515), highestPrice.stripTrailingZeros());

    }
}
