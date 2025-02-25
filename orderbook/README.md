# How to Run the Application

## Steps to Start:
1. Clone the code to your environment.
2. Open it in your preferred IDE, such as **IntelliJ**.
3. Run `OrderbookApplication`.
4. Call the given APIs or open [`http://localhost:8080/swagger-ui/index.html#/`](http://localhost:8080/swagger-ui/index.html#/) in your browser.

This application runs a local **H2 database**.

---

## Given APIs:

### **Order Endpoints**
- **Create a new order**
  ```http
  POST http://localhost:8080/orders
  ```

- **Get order by ID**
  ```http
  GET http://localhost:8080/orders/orderById/{id}
  ```

### **Price Endpoints**
- **Get the lowest price for a ticker on a date**
  ```http
  GET http://localhost:8080/orders/lowest_price/{ticker}/{date}
  ```

- **Get the highest price for a ticker on a date**
  ```http
  GET http://localhost:8080/orders/highest_price/{ticker}/{date}
  ```

- **Get the average price for a ticker on a date**
  ```http
  GET http://localhost:8080/orders/average_price/{ticker}/{date}
  ```

- **Get the total number of orders for a ticker on a date**
  ```http
  GET http://localhost:8080/orders/amount/{ticker}/{date}
  ```
---

