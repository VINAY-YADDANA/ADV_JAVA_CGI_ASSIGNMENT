# Employee Leave Management (Spring Boot + Thymeleaf + H2)

A minimal web-based system to manage Employees and their Leave Requests.

## Tech
- Spring Boot 3 (Web, Data JPA, Validation, Thymeleaf)
- H2 in-memory database
- Maven
- Java 17

## Run
```bash
mvn spring-boot:run
# or build a jar
mvn clean package
java -jar target/employee-leave-management-0.0.1-SNAPSHOT.jar
```
Then open: http://localhost:8080

- Dashboard: `/` or `/dashboard`
- Employees: `/employees`
- Leave Requests: `/leaves`
- H2 Console: `/h2-console` (JDBC URL: `jdbc:h2:mem:leave_mgmt`, username `sa`, blank password)

## Features
- CRUD for Employees (create, update, delete, list)
- Submit leave requests; Approve/Reject
- Filter leave requests by status and/or employee
- Dashboard tiles: total employees, total leaves, pending approvals
- Server-side validation: required fields, email, date range (end >= start)
- Simple Thymeleaf front-end
