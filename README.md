# Web Application for House and Tenant Management

## Modules
* housing:Manages houses and tenants.
* cache-starter:Custom Spring Boot starter for caching the service layer.
* exception-handling-starter: Custom Spring Boot starter for handling exceptions.
* logging-starter: Custom Spring Boot starter for logging.

## Description
This web application is designed for managing houses and their tenants using Spring Boot. 
The system provides a REST API for performing CRUD operations on House and Person entities.

## Database Initialization with Liquibase and Docker
The application's database is initialized using Liquibase, 
and the entire setup is containerized using Docker. 
The `docker-compose.yml` file orchestrates the PosgresSQL database and Liquibase for migrations.

## Multi-Project Configuration
The project is structured as a multi-project with modules named "housing," "cache-starter," "exception-handling-starter," and "logging-starter."
## Entities
### House
- id (Database identifier)
- uuid (Unique identifier for the house)
- area (Area of the house)
- country (Country where the house is located)
- city (City where the house is located)
- street (Street where the house is situated)
- number (House number)
- create_date (Creation date of the house)
- update_date (Last update date of the house record)
- owners (List of house owners)
- tenants (List of house residents)

### Person
- id (Database identifier)
- uuid (Unique identifier for the person)
- name (First name)
- surname (Last name)
- sex (Gender: Male or Female)
- passport_series (Passport series)
- passport_number (Passport number)
- create_date (Creation date of the person record)
- update_date (Last update date of the person record)
- residency (House where the person resides)

### HouseHistory 
#### to track historical changes in house ownership and residency.
- id (Database identifier)
- house_id (Reference to the house)
- person_id (Reference to the person)
- date (Date of the history record)
- type (Type of history: OWNER or TENANT)

## REST API
### House API
- **GET /houses**: Get a list of all houses with pagination (default page size: 15).
- **GET /houses/{id}**: Get information about the house with the specified identifier.
- **POST /houses**: Create a new house.
- **PUT /houses/{id}**: Update information about the house with the specified identifier.
- **DELETE /houses/{id}**: Delete the house with the specified identifier.

### Person API
- **GET /people**: Get a list of all people with pagination (default page size: 15).
- **GET /people/{id}**: Get information about the person with the specified identifier.
- **POST /persons**: Create a new person record.
- **PUT /people/{id}**: Update information about the person with the specified identifier.
- **DELETE /people/{id}**: Delete the person record with the specified identifier.

### Additional APIs
- **GET /houses/{houseId}/residents**: Get a list of all residents of the house with the specified identifier.
- **GET /persons/{personId}/owned-houses**: Get a list of all houses owned by the person with the specified identifier.

### HouseHistory APIs

- **GET /house-history/{houseId}/previous-residents**: Get a list of people who have ever lived in the house
- **GET /house-history/{houseId}/previous-owners**: Get a list of people who have ever owned a house
- **GET /house-history/{personId}/previous-residency**: Get a list of houses where the Person lived
- **GET /house-history/{personId}/previous-owned-houses**: Get a list of houses that a Person has ever owned


## Configuration File: application.yml
```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: postgres
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show_sql: true
    format_sql: true
cache:
  size: 16
  type: LRU
```

## Custom Spring Boot Starters Configuration
### Cache Starter
```
Prefix: cache.type
Type: java.lang.String
Description: The type of cache used.
Default Value: "LRU"
```

```
Prefix: cache.size
Type: java.lang.Integer
Description: The size of the used cache.
Default Value: 16
```
### Exception Handling Starter
```
Prefix: spring.exception-handling.enabled
Type: java.lang.Boolean
Description: Enable exception handling.
Default Value: true
```
### Logging Starter
```
Prefix: spring.logging.enabled
Type: java.lang.Boolean
Description: Enables custom logging.
Default Value: true
```