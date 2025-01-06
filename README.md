# TechGalaxy Store Backend

This repository contains the backend implementation for the **Tech Galaxy Store** system. It provides a RESTful API for managing the store's functionalities such as product listing, user management, and order processing.

## Features

- **User Management**: Registration, login, and profile management.
- **Role-Based Access Control**: 
  - Predefined roles with specific permissions.
  - Customizable permissions for each account, allowing two accounts with the same role to have different permissions.
- **Product Management**: CRUD operations for products and categories.
- **Order Processing**: Handle cart, orders, and payments.
- **Payment Integration**: Support for VNPay payment gateway.
- **API Documentation**: Swagger/OpenAPI support for easy API exploration.

## Technology Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **API**: RESTful API
- **Build Tool**: Maven

## Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- Maven 
- MySQL

## Getting Started

Clone the repository:
   git clone [https://github.com/minhducn14/TechGalaxy_BE](https://github.com/minhducn14/TechGalaxy_BE)


### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Rest Repositories](https://docs.spring.io/spring-boot/3.3.5/how-to/data-access.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.3.5/reference/using/devtools.html)
* [Validation](https://docs.spring.io/spring-boot/3.3.5/reference/io/validation.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Front-end admin
<a href="https://github.com/PhamVanThanh2111/TechGalaxyFEAdmin">Admin</a>

### Front-end customer
<a href="https://github.com/Kha03/PhoneStore_GUI">Customers</a>
