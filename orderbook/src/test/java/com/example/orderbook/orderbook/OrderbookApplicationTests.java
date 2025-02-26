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
    public void findAllOrdersBetweenTwoIndexes() throws InterruptedException {

        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.BUY, 500, BigDecimal.valueOf(20.7515), Currencies.USD));
        Thread.sleep(200);
        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.BUY, 400, BigDecimal.valueOf(15.1515), Currencies.USD));
        Thread.sleep(200);
        orderBookRepository.save(new Order(Tickers.TSLA, ExchangeType.SELL, 200, BigDecimal.valueOf(150.5351), Currencies.SEK));
        Thread.sleep(200);
        orderBookRepository.save(new Order(Tickers.GME, ExchangeType.BUY, 400, BigDecimal.valueOf(250.7586), Currencies.USD));
        Thread.sleep(200);
        orderBookRepository.save(new Order(Tickers.SAVE, ExchangeType.BUY, 700, BigDecimal.valueOf(10.5386), Currencies.USD));

        int limit = 3;
        int offset = 1;
        List<Order> orders = orderBookRepository.findOrdersByIndex(limit, offset);
        Assertions.assertEquals(3, orders.size());
        Assertions.assertEquals(400, orders.get(0).getVolume());
    }

    @Test
    public void findOrderById() throws InterruptedException {

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

//	@Mock
//	private OrderBookRepository orderBookRepository;
//
//	@InjectMocks
//	private OrderService orderService;
//
//	@Test
//	public void OrderService_RetrieveNumberOfOrdersForATickerAndDate() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.SELL, 300, BigDecimal.valueOf(155.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order3 = new Order(UUID.randomUUID(), Tickers.GME, ExchangeType.BUY, 300, BigDecimal.valueOf(160.53515), Currencies.EUR, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order4 = new Order(UUID.randomUUID(), Tickers.SAVE, ExchangeType.BUY, 300, BigDecimal.valueOf(160.53515), Currencies.SEK, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2, order3, order4));
//
//		when(orderBookRepository.findAll()).thenReturn(orders);
//		int numberOfOrders = orderService.numberOfOrdersByTickerAndDate(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24));
//
//		Assertions.assertEquals(numberOfOrders, 2);
//	}
//
//	@Test
//	public void OrderService_RetrieveNumberOfOrdersForATickerAndDate_DateDoesntExist() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, BigDecimal.valueOf(155.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));
//
//		when(orderBookRepository.findAll()).thenReturn(orders);
//		int numberOfOrders = orderService.numberOfOrdersByTickerAndDate(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));
//
//		Assertions.assertEquals(numberOfOrders, 0);
//	}
//
//	@Test
//	public void OrderService_CreateAnOrder_VerifyThatItWasCreated() {
//		Order order = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.save(Mockito.any(Order.class))).thenReturn(order);
//		Order mockOrder = orderService.addOrder(order);
//
//		Assertions.assertEquals(Tickers.TSLA, mockOrder.getTicker());
//		Assertions.assertEquals(ExchangeType.BUY, mockOrder.getExchangetype());
//		Assertions.assertEquals( 200, mockOrder.getVolume());
//		Assertions.assertEquals(BigDecimal.valueOf(150.53515), mockOrder.getPrice());
//		Assertions.assertEquals(Currencies.USD, mockOrder.getCurrency());
//		Assertions.assertEquals(LocalDate.of(2024, 2, 24), mockOrder.getCreatedAt().toLocalDate());
//	}
//
//	@Test
//	public void OrderService_RetrieveOrderById() {
//		UUID uuid = UUID.randomUUID();
//		Order order = new Order(uuid, Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findById(uuid)).thenReturn(Optional.of(order));
//		Order mockOrder = orderService.getOrderById(uuid).get();
//
//		Assertions.assertEquals(Tickers.TSLA, mockOrder.getTicker());
//		Assertions.assertEquals(ExchangeType.BUY, mockOrder.getExchangetype());
//		Assertions.assertEquals( 200, mockOrder.getVolume());
//		Assertions.assertEquals(BigDecimal.valueOf(150.53515), mockOrder.getPrice());
//		Assertions.assertEquals(Currencies.USD, mockOrder.getCurrency());
//		Assertions.assertEquals(LocalDate.of(2024, 2, 24), mockOrder.getCreatedAt().toLocalDate());
//	}
//
//	@Test
//	public void OrderService_RetrieveOrderById_IdIsntFound() {
//		UUID uuidDoesntExist = UUID.fromString("5a1a843d-d632-44a2-b237-dbf35b875555");
//
//		when(orderBookRepository.findById(uuidDoesntExist)).thenReturn(Optional.empty());
//		Optional<Order> mockOrder = orderService.getOrderById(uuidDoesntExist);
//		Assertions.assertEquals(Optional.empty(), mockOrder);
//	}
//
//	@Test
//	public void OrderService_RetrieveHighestPriceByTickerOnDate() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
//		BigDecimal highestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();
//
//		Assertions.assertEquals(BigDecimal.valueOf(155.53515), highestPrice);
//	}
//
//	@Test
//	public void OrderService_RetrieveHighestPriceByTickerOnDate_DateDoesntExist() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(List.of(order1));
//		Optional<BigDecimal> highestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));
//
//		Assertions.assertEquals(Optional.empty(), highestPrice);
//	}
//
//	@Test
//	public void OrderService_RetrieveAveragePriceByTickerOnDate() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
//		BigDecimal averagePrice = orderService.getAveragePrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();
//
//		Assertions.assertEquals(BigDecimal.valueOf(153.03515), averagePrice);
//	}
//
//	@Test
//	public void OrderService_RetrieveAveragePriceByTickerOnDate_DateDoesntExist() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
//		Optional<BigDecimal> averagePrice = orderService.getAveragePrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));
//
//		Assertions.assertEquals(Optional.empty(), averagePrice);
//	}
//
//	@Test
//	public void OrderService_RetrieveLowestPriceByTickerOnDate() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
//		BigDecimal lowestPrice = orderService.getMinPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();
//
//		Assertions.assertEquals(BigDecimal.valueOf(150.53515), lowestPrice);
//	}
//
//	@Test
//	public void OrderService_RetrieveLowestPriceByTickerOnDate_DateDoesntExist() {
//		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
//
//		when(orderBookRepository.findAll()).thenReturn(List.of(order1));
//		Optional<BigDecimal> lowestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));
//
//		Assertions.assertEquals(Optional.empty(), lowestPrice);
//	}
}
