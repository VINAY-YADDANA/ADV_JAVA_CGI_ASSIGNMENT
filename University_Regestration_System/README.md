# University Course Registration Console (JDBC + Java 8 Streams)

Console-based MVC app that manages Students, Courses, and Registrations using JDBC for persistence and Java 8 Streams for business logic in the Service layer.

## Features (mapped to menu)
1. Add Student (DAO insert)
2. Add Course (DAO insert)
3. Register Student for Course (DAO insert into registrations)
4. View All Students with Registered Courses (DAO JOIN + Service `groupingBy`)
5. Search Courses by Minimum Credit Requirement (Service `filter()`)
6. Get Students Registered in a Particular Course (Service `filter()` + `map()`)
7. Sort Students by Year and then by Name (`Comparator.comparing()` with Streams)
8. Calculate Total Credits Per Student (`groupingBy()` + `summingInt()`)

## Project structure
```
src/main/java/com/example/university/
  controller/App.java         # Console controller (menu)
  service/UniversityService.java
  dao/*.java                  # JDBC DAOs
  model/*.java                # POJOs
  dto/StudentCourseJoin.java  # JOIN row
  util/DBUtil.java            # Loads db.properties and manages connections
src/main/resources/db.properties
schema.sql                    # DDL
sample_data.sql               # Optional seed data
pom.xml
```



## Build & Run
Requires Java 8+ and Maven.

```bash
mvn -q exec:java
```
Launch h2 console on localhost:8888 and run 
```bash
RUNSCRIPT FROM 'schema.sql';
RUNSCRIPT FROM 'sample_data.sql';
```