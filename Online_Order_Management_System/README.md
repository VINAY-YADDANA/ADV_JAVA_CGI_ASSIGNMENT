# Online Order Management Microservices

Three Spring Boot modules:

- **eureka-server** – Service registry (port `8761`)
- **product-service** – Product CRUD + stock management (port `8881`)
- **order-service** – Order CRUD + calls product-service to reserve/restock stock (port `8082`)

## Run (in three terminals)

```bash
# Terminal 1
cd eureka-server
./mvnw spring-boot:run

# Terminal 2
cd product-service
./mvnw spring-boot:run

# Terminal 3
cd order-service
./mvnw spring-boot:run
```

> If `mvnw` files are not present, use `mvn spring-boot:run`

Eureka dashboard: http://localhost:8761

H2 consoles:
- Product Service: http://localhost:8881/h2 (JDBC URL: `jdbc:h2:mem:productsdb`)
- Order Service: http://localhost:8082/h2 (JDBC URL: `jdbc:h2:mem:ordersdb`)

## REST endpoints

### Product Service

- `POST /api/products` – create product
- `GET /api/products` – list products
- `GET /api/products/{id}` – get by id
- `PUT /api/products/{id}` – update
- `DELETE /api/products/{id}` – delete
- `POST /api/products/{id}/reserve?qty=5` – **atomically reserve** stock (409 if insufficient)
- `POST /api/products/{id}/restock?qty=5` – add stock (used when an order is cancelled)

### Order Service

- `POST /api/orders` – place order (validates product + reserves stock)
  ```json
  { "productId": 1, "quantity": 2, "customerName": "Alice" }
  ```
- `GET /api/orders` – list orders
- `GET /api/orders/{id}` – get by id
- `PATCH /api/orders/{id}/status` – update order status
  ```json
  { "status": "COMPLETED" } // or CANCELLED (which restocks)
  ```
- `DELETE /api/orders/{id}` – delete order

## cURL quick test

```bash
# Create an order for product 1 (stock seeded by data.sql)
curl -X POST http://localhost:8082/api/orders   -H "Content-Type: application/json"   -d '{"productId":1,"quantity":2,"customerName":"Alice"}'

# Update status to COMPLETED
curl -X PATCH http://localhost:8082/api/orders/1/status   -H "Content-Type: application/json"   -d '{"status":"COMPLETED"}'

# Cancel (this will restock the product quantity)
curl -X PATCH http://localhost:8082/api/orders/1/status   -H "Content-Type: application/json"   -d '{"status":"CANCELLED"}'
```

## Notes

- Validation is enabled on both services (e.g., quantity > 0, required fields).
- Order Service uses **OpenFeign** to call Product Service by **service name** through Eureka.
- Product stock changes use a pessimistic write lock to avoid race conditions.
- Each service has its own **H2** in-memory DB.
