# FinPulse API

A RESTful API for personal finance management, built with Spring Boot and PostgreSQL. Handles user authentication, transactions, categories, budgets, and financial reporting — all secured with JWT.

**Live API:** https://finpulse-api-iuf2.onrender.com
**Swagger Docs:** https://finpulse-api-iuf2.onrender.com/swagger-ui/index.html
**Frontend Repo:** https://github.com/Petersobi/finpulse-ui

---

## Features

- JWT-based authentication with BCrypt password hashing
- Full CRUD for transactions, categories, and budgets
- Monthly and period-based transaction filtering
- Dashboard endpoint with income, expense, and balance summaries
- Duplicate prevention for categories and budgets via custom exceptions
- Global exception handling with clean, consistent error responses
- Unit tests for the authentication service (JUnit 5 + Mockito)
- API documentation with Swagger UI
- Dockerized and deployed on Render

---

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.3.11
- **Database:** PostgreSQL
- **Security:** Spring Security, JWT (JJWT)
- **Documentation:** Springdoc OpenAPI (Swagger UI)
- **Testing:** JUnit 5, Mockito
- **Build Tool:** Maven
- **Deployment:** Docker, Render

---

## Project Structure

```
src/main/java/com/finpulse/api/
├── entity/        # JPA entities (User, Transaction, Category, Budget)
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic
├── controller/      # REST endpoints
├── dto/            # Request/response objects
└── security/        # JWT, Spring Security config
```

---

## Getting Started Locally

### Prerequisites
- Java 21
- Maven
- PostgreSQL

### Setup

1. Clone the repository
```bash
git clone https://github.com/Petersobi/FinPulse-API.git
cd FinPulse-API
```

2. Create a PostgreSQL database named `finpulse`

3. Configure `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finpulse
spring.datasource.username=postgres
spring.datasource.password=your_password
jwt.secret=your_secret_key
jwt.expiration=86400000
```

4. Run the application
```bash
./mvnw spring-boot:run
```

5. Visit `http://localhost:8081/swagger-ui/index.html` to explore the API

---

## API Endpoints Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |
| GET/POST | `/api/transactions` | List or create transactions |
| GET/PUT/DELETE | `/api/transactions/{id}` | Manage a single transaction |
| GET | `/api/transactions/monthly` | Get transactions for a given month |
| GET | `/api/transactions/period` | Get transactions for a date range |
| GET/POST | `/api/categories` | List or create categories |
| GET/PUT/DELETE | `/api/categories/{id}` | Manage a single category |
| GET/POST | `/api/budgets` | List or create budgets |
| PUT/DELETE | `/api/budgets/{id}` | Manage a single budget |
| GET | `/api/dashboard` | Financial summary |

Full interactive documentation available via Swagger UI.

---

## Author

**Peter Somto Obi**
[LinkedIn](#) · [GitHub](https://github.com/Petersobi)
