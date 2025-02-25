package com.example.orderbook.orderbook;

import com.example.orderbook.orderbook.model.Order;
import com.example.orderbook.orderbook.repository.OrderBookRepository;
import com.example.orderbook.orderbook.service.OrderService;
import com.example.orderbook.orderbook.enums.Currencies;
import com.example.orderbook.orderbook.enums.ExchangeType;
import com.example.orderbook.orderbook.enums.Tickers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderbookApplicationTests {

	@Mock
	private OrderBookRepository orderBookRepository;

	@InjectMocks
	private OrderService orderService;

	@Test
	public void OrderService_RetrieveNumberOfOrdersForATickerAndDate() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.SELL, 300, BigDecimal.valueOf(155.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order3 = new Order(UUID.randomUUID(), Tickers.GME, ExchangeType.BUY, 300, BigDecimal.valueOf(160.53515), Currencies.EUR, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order4 = new Order(UUID.randomUUID(), Tickers.SAVE, ExchangeType.BUY, 300, BigDecimal.valueOf(160.53515), Currencies.SEK, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2, order3, order4));

		when(orderBookRepository.findAll()).thenReturn(orders);
		int numberOfOrders = orderService.numberOfOrdersByTickerAndDate(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24));

		Assertions.assertEquals(numberOfOrders, 2);
	}

	@Test
	public void OrderService_RetrieveNumberOfOrdersForATickerAndDate_DateDoesntExist() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, BigDecimal.valueOf(155.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));

		when(orderBookRepository.findAll()).thenReturn(orders);
		int numberOfOrders = orderService.numberOfOrdersByTickerAndDate(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));

		Assertions.assertEquals(numberOfOrders, 0);
	}

	@Test
	public void OrderService_CreateAnOrder_VerifyThatItWasCreated() {
		Order order = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.save(Mockito.any(Order.class))).thenReturn(order);
		Order mockOrder = orderService.addOrder(order);

		Assertions.assertEquals(Tickers.TSLA, mockOrder.getTicker());
		Assertions.assertEquals(ExchangeType.BUY, mockOrder.getExchangetype());
		Assertions.assertEquals( 200, mockOrder.getVolume());
		Assertions.assertEquals(BigDecimal.valueOf(150.53515), mockOrder.getPrice());
		Assertions.assertEquals(Currencies.USD, mockOrder.getCurrency());
		Assertions.assertEquals(LocalDate.of(2024, 2, 24), mockOrder.getCreatedAt().toLocalDate());
	}

	@Test
	public void OrderService_RetrieveOrderById() {
		UUID uuid = UUID.randomUUID();
		Order order = new Order(uuid, Tickers.TSLA, ExchangeType.BUY, 200, BigDecimal.valueOf(150.53515), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findById(uuid)).thenReturn(Optional.of(order));
		Order mockOrder = orderService.getOrderById(uuid).get();

		Assertions.assertEquals(Tickers.TSLA, mockOrder.getTicker());
		Assertions.assertEquals(ExchangeType.BUY, mockOrder.getExchangetype());
		Assertions.assertEquals( 200, mockOrder.getVolume());
		Assertions.assertEquals(BigDecimal.valueOf(150.53515), mockOrder.getPrice());
		Assertions.assertEquals(Currencies.USD, mockOrder.getCurrency());
		Assertions.assertEquals(LocalDate.of(2024, 2, 24), mockOrder.getCreatedAt().toLocalDate());
	}

	@Test
	public void OrderService_RetrieveOrderById_IdIsntFound() {
		UUID uuidDoesntExist = UUID.fromString("5a1a843d-d632-44a2-b237-dbf35b875555");

		when(orderBookRepository.findById(uuidDoesntExist)).thenReturn(Optional.empty());
		Optional<Order> mockOrder = orderService.getOrderById(uuidDoesntExist);
		Assertions.assertEquals(Optional.empty(), mockOrder);
	}

	@Test
	public void OrderService_RetrieveHighestPriceByTickerOnDate() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
		BigDecimal highestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();

		Assertions.assertEquals(BigDecimal.valueOf(155.53515), highestPrice);
	}

	@Test
	public void OrderService_RetrieveHighestPriceByTickerOnDate_DateDoesntExist() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(List.of(order1));
		Optional<BigDecimal> highestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));

		Assertions.assertEquals(Optional.empty(), highestPrice);
	}

	// test get average price
	@Test
	public void OrderService_RetrieveAveragePriceByTickerOnDate() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
		BigDecimal averagePrice = orderService.getAveragePrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();

		Assertions.assertEquals(BigDecimal.valueOf(153.03515), averagePrice);
	}

	@Test
	public void OrderService_RetrieveAveragePriceByTickerOnDate_DateDoesntExist() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
		Optional<BigDecimal> averagePrice = orderService.getAveragePrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));

		Assertions.assertEquals(Optional.empty(), averagePrice);
	}

	@Test
	public void OrderService_RetrieveLowestPriceByTickerOnDate() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));
		Order order2 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 300, new BigDecimal("155.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
		BigDecimal lowestPrice = orderService.getMinPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 24)).get();

		Assertions.assertEquals(BigDecimal.valueOf(150.53515), lowestPrice);
	}

	@Test
	public void OrderService_RetrieveLowestPriceByTickerOnDate_DateDoesntExist() {
		Order order1 = new Order(UUID.randomUUID(), Tickers.TSLA, ExchangeType.BUY, 200, new BigDecimal("150.53515"), Currencies.USD, LocalDateTime.of(2024, 2, 24, 15, 30, 0));

		when(orderBookRepository.findAll()).thenReturn(List.of(order1));
		Optional<BigDecimal> lowestPrice = orderService.getMaxPrice(Tickers.TSLA.toString(), LocalDate.of(2024, 2, 25));

		Assertions.assertEquals(Optional.empty(), lowestPrice);
	}


}
