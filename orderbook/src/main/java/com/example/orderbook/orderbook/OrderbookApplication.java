package com.example.orderbook.orderbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderbookApplication {

	// fix the dates in max/min/avg
	// remove most of the repositorymethods and do the same thing in the service layers instead using
	// findAll and stream.filter on dates and on ticker and find lowest/highest/average

	public static void main(String[] args) {
		SpringApplication.run(OrderbookApplication.class, args);
	}

}
