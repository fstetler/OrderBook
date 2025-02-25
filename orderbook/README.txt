How to run the application:

1. Clone the code to your environment.
2. Start it in your preferred IDE, such as intelliJ.
3. Run 'OrderbookApplication'
4. Call the given APIS or run 'http://localhost:8080/swagger-ui/index.html#/' in your browser
This application runs a local H2 database

Given APIs:
GET http://localhost:8080/orders
POST http://localhost:8080/orders
GET http://localhost:8080/orders/{ticker}/{date}
GET http://localhost:8080/orders/orderById/{id}
GET http://localhost:8080/orders/lowest_price/{ticker}/{date}
GET http://localhost:8080/orders/highest_price/{ticker}/{date}
GET http://localhost:8080/orders/average_price/{ticker}/{date}
GET http://localhost:8080/orders/amount/{ticker}/{date}



