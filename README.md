# QikServe technical test

- [Answers for the following up questions](https://github.com/brerodrigues/Qikserv-Technical-Test/blob/master/follow-up-question.md)
- [Answer for the main question](https://github.com/brerodrigues/Qikserv-Technical-Test/blob/master/main-question.md)
- [Thought process](https://github.com/brerodrigues/Qikserv-Technical-Test/blob/master/thought-process.md)

## Project Overview

This project is a solution to the Qikserve challenge. It is built using Java and the Spring Boot framework.

## API Usage Instructions

### Get Products
- Retrieve all products:
```bash
curl --location --request GET 'http://localhost:8181/products'
```

- Retrieve a specific product by ID:
```bash
curl --location --request GET 'http://localhost:8181/products/{product_id}'
```

### Shopping Cart Operations

- Retrieve all products in the shopping cart:
```bash
curl --location --request GET 'http://localhost:8181/cart/items'
```

- Add a product to the shopping cart by its ID:
```bash
curl --location --request POST 'http://localhost:8181/cart/add/{product_id}'
```
- Retrieve the total value of the items in the shopping cart:
```bash
curl --location --request GET 'http://localhost:8181/cart/total'
```
- Retrieve the total savings achieved through promotions applied to the shopping cart:
```bash
curl --location --request GET 'http://localhost:8181/cart/savings'
```

## Project information

### Dependencies

- **Spring Boot Starter Web**: Starter for building web, including RESTful, applications using Spring MVC.
- **Spring Boot DevTools**: Provides fast application restarts, LiveReload, and configuration support.
- **Spring Boot Starter Test**: Starter for testing Spring Boot applications with libraries including JUnit, Hamcrest, and Mockito.
- **Spring WebFlux**: Provides reactive programming support for web applications.
- **Reactor Test**: Provides testing utilities for projects that use Reactor.

### Build Tools

- **Spring Boot Maven Plugin**: Allows us to package the application as an executable JAR or WAR archive.
- **Maven Compiler Plugin**: Configures the Java source and target compatibility for the project.

### Java Version

The project is configured to use Java version 21.

### Project Structure

The project follows the standard Maven project structure and includes the necessary configuration files for building and running the application.


