<a id="readme-top"></a>

<!-- PROJECT SHIELDS -->
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#how-to-explore">How To Explore The Repository</a></li>
      </ul>
    </li>
    <li>
      <a href="#project-implementation">Project Implementation</a>
      <ul>
        <li><a href="#git-workflow">Git Workflow</a></li>
        <li><a href="#hexagonal-architecture">Hexagonal Architecture</a></li>
        <li><a href="#testing">Testing</a></li>
        <li><a href="#project-configuration">Project Configuration</a></li>
      </ul>
    </li>
    <li>
      <a href="#usage">Usage</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#run-the-application">Run The Application</a></li>
        <li><a href="#use-the-application">Use The Application</a></li>
      </ul>
    </li>
    <li><a href="#contribution">Contribution</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

<a id="about-the-project"></a>

This project is a technical demonstration of how to build a simple project using Java 21 and Spring Boot. This project
also wants to reflect clean code and clean architecture techniques and best practices.

The purpose of the application is to list the assigned prices in a database table. You can filter the results using
parameters such as price application date, brand or product ID. If you are filtering by date and two or more prices
match for the same date prices match for the same application date, the one with the highest priority will be returned.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

<a id="built-with"></a>

The application was created using [Spring Initializr](https://start.spring.io/) based on **Spring Boot 3.3.3** and *
*java 21**.

These are the tools with which the application has been built:

* [![SpringBoot][springboot]][springboot-url]
* [![Java][java]][java-url]
* [![Junit][junit]][junit-url]
* [![Mockito][mockito]][mockito-url]
* [![Swagger][swagger]][swagger-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### How To Explore The Repository

<a id="how-to-explore"></a>

The repository was tagged each time a significant change was made so that the entire development process could be
explored.

These are the different tags available:

* [base-project](https://github.com/SaulEiros/price-explorer/tree/base-project): The project created with Spring
  Initializr
* [base-structure](https://github.com/SaulEiros/price-explorer/tree/base-structure): Added domain objects, separated the
  application into layers and added service interfaces.
* [price-service-impl](https://github.com/SaulEiros/price-explorer/tree/price-service-impl): Added Mockito
  dependency, added Price Service Unit Tests and Implementation.
* [output-adapters-impl](https://github.com/SaulEiros/price-explorer/tree/output-adapters-impl): Added H2 Integration
  and
  implementation the Sql Price Repository.
* [input-adapters-impl](https://github.com/SaulEiros/price-explorer/tree/input-adapters-impl): Added Swagger
  dependencies and Rest Controllers implementation.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Project Implementation

<a id="project-implementation"></a>

This section explores how different aspects of development were addressed for the reader's clarity.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Git Workflow

<a id="git-flow"></a>

All the developments were done in a **feature branch** and merged to **main** once the branch scope was complete. Once
the changes reach main, a new **tag** is created so that they can be easily located.

Also, Conventional Commits specification ([see more](https://www.conventionalcommits.org/en/v1.0.0/)) was followed.

Perhaps in a real scenario, merging the changes by squashing commits would have been appropriate, but in this case I
have chosen to merge all commits so that the entire development process could be seen.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Hexagonal Architecture

<a id="hexagonal-architecture"></a>

Hexagonal Architecture, also known as Ports and Adapters
Architecture ([read more](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))), is an architectural pattern
that aims to create applications with low coupling between its different components.

This allows its components to be easily replaced without compromising other parts of the software. In addition, it also
facilitates the maintenance and extension of the code, as well as its testing.

It may seem that for such a simple application it is not necessary. But if, in the future, we would like to add more
functionality, integrate with a database to create a real repository of images or add extra functionality, having
developed the application following this pattern will make the process much easier.

The main code is separated into the following folder hierarchy:

```bash
main
├── AlbumExplorerApplication.kt
├── application
    ...
├── domain
    ...
└── infrastructure
    ...
```

* **application**: This layer contains the implementation of the application's business logic. It also defines
  interfaces of input adapters (services that implement the business logic) and output adapters (connections to external
  repositories and services).
* **domain**: This layer implements the objects that define the core elements of the business.
* **infrastructure**: This layer implements the input and output adapters (The input and output connections with
  external
  services.)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Testing

<a id="testing"></a>

Unit tests for the application layer were developed using **Junit 5** and **Mockito**. Also,
TTD ([see more](https://en.wikipedia.org/wiki/Test-driven_development)) was followed, creating first the test suit and
implementing the services later.

To track this during development, you can consult the commit history for the
tags [price-service-impl](https://github.com/SaulEiros/price-explorer/tree/price-service-impl).

Additionally, to structure the tests, I have tried to follow the **GIVEN/THEN/WHEN**
pattern ([see more](https://en.wikipedia.org/wiki/Given-When-Then)).

Here is an example of such tests:

```java

@Test
void givenNoFilters_whenGetAllPrices_thenReturnAllPrices() {
    // GIVEN
    PriceFilter filter = new PriceFilter(
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );

    when(priceRepository.findPrices(any())).thenReturn(prices);

    // WHEN
    List<Price> result = priceService.findPrices(filter);

    // THEN
    ArgumentCaptor<PriceQuery> captor = ArgumentCaptor.forClass(PriceQuery.class);
    verify(priceRepository, Mockito.times(1)).findPrices(any());
    verify(priceRepository).findPrices(captor.capture());

    PriceQuery capturedQuery = captor.getValue();

    assertEquals(Optional.empty(), capturedQuery.date());
    assertEquals(Optional.empty(), capturedQuery.productId());
    assertEquals(Optional.empty(), capturedQuery.brandId());

    assertNotNull(result);
    assertEquals(prices, result);
}
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Project Configuration

<a id="project-configuration"></a>

There are a few properties configured for the smooth running of the project:

```properties
spring.application.name=priceexplorer
# H2 Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.sql.init.platform=h2
spring.jpa.defer-datasource-initialization=true
# H2 Console
spring.h2.console.enabled=false
spring.h2.console.path=/h2-console
spring.jpa.show-sql=true
```

It basically contains the configuration of the H2 In Memory database. Special attention to the property
`spring.jpa.defer-datasource-initialization=true` that will allow us to execute the `data.sql` file (located
in `src/main/resources`) after the creation of the schemas by **Hibernate**.

This is the content of `data.sql` to initialize our data when the application is loaded.

```sql
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency, last_update,
                    last_update_by)
VALUES (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR', '2020-03-26 14:49:07', 'user1'),
       (1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR', '2020-05-26 15:38:22', 'user1'),
       (1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR', '2020-05-26 15:39:22', 'user2'),
       (1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR', '2020-06-02 10:14:00', 'user1');
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage

<a id="usage"></a>

### Prerequisites

<a id="prerequisites"></a>

If you want to run the application, you must make sure that you have the following dependencies installed:

* [Java 21](https://www.java.com)
* [Gradle](https://docs.gradle.org/current/userguide/installation.html)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Run The Application

<a id="run-the-application"></a>

To run the application it is necessary to type the following commands:

```bash
gradle build
```

```bash
gradle bootRun
```

If you only want to run the test, run the following command:

```bash
gradle test
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Use The Application

<a id="use-the-application"></a>

To use the application you can access the Swagger UI. To do so, you can open a browser and go to this url:

```http request
http://localhost:8080/swagger-ui/index.html
```

You can also perform request to the API invoking directly the developed endpoint as in the following examples:

#### Get All Prices.

This will return the whole collection of prices.

```http request
http://localhost:8080/prices
```

<details>
  <summary>Response</summary>

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "price": 35.5,
    "currency": "EUR",
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "price": 25.45,
    "currency": "EUR",
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 3,
    "price": 30.5,
    "currency": "EUR",
    "startDate": "2020-06-15T00:00:00",
    "endDate": "2020-06-15T11:00:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 4,
    "price": 38.95,
    "currency": "EUR",
    "startDate": "2020-06-15T16:00:00",
    "endDate": "2020-12-31T23:59:59"
  }
]
```

</details>

#### Filter by Date

The date must follow the **ISO 8601** Standard for Date and Time. Check
it [here](https://www.iso.org/iso-8601-date-and-time-format.html).

```http request
http://localhost:8080/prices?date=2020-06-14T15:30:00
```

<details>
  <summary>Response</summary>

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "price": 25.45,
    "currency": "EUR",
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00"
  }
]
```

</details>

If the given date does not match any result, an empty list will be returned.

#### Filter by Brand Id

The Brand Id should be a Long.

```http request
http://localhost:8080/prices?brandId=1
```

<details>
  <summary>Response</summary>

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "price": 35.5,
    "currency": "EUR",
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "price": 25.45,
    "currency": "EUR",
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 3,
    "price": 30.5,
    "currency": "EUR",
    "startDate": "2020-06-15T00:00:00",
    "endDate": "2020-06-15T11:00:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 4,
    "price": 38.95,
    "currency": "EUR",
    "startDate": "2020-06-15T16:00:00",
    "endDate": "2020-12-31T23:59:59"
  }
]
```

</details>

If the given Brand Id does not match any result, an empty list will be returned.

#### Filter by Product Id

```http request
http://localhost:8080/prices?productId=35455
```

<details>
  <summary>Response</summary>

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "price": 35.5,
    "currency": "EUR",
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "price": 25.45,
    "currency": "EUR",
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 3,
    "price": 30.5,
    "currency": "EUR",
    "startDate": "2020-06-15T00:00:00",
    "endDate": "2020-06-15T11:00:00"
  },
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 4,
    "price": 38.95,
    "currency": "EUR",
    "startDate": "2020-06-15T16:00:00",
    "endDate": "2020-12-31T23:59:59"
  }
]
```

</details>

If the given Product Id does not match any result, and empty list will be returned.

#### Filter by All Params

You can use all the params at the same time. Remember that when filtering by date, if two or more results match, the one
with higher priority will be returned.

```http request
http://localhost:8080/prices?date=2020-06-15T07:30:00&brandId=1&productId=35455
```

<details>
  <summary>Response</summary>

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 3,
    "price": 30.5,
    "currency": "EUR",
    "startDate": "2020-06-15T00:00:00",
    "endDate": "2020-06-15T11:00:00"
  }
]
```

</details>

If any of the parameter do not match any result, an empty list will be returned.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Contribution

<a id="contribution"></a>

The project has been developed for training purposes, so if you have any suggestions, you are more than welcome to leave
a comment by opening an Issue in the repository or contacting me through my LinkedIn.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[issues-shield]: https://img.shields.io/github/issues/sauleiros/price-explorer.svg?style=for-the-badge

[issues-url]: https://github.com/sauleiros/price-explorer/issues

[license-shield]: https://img.shields.io/github/license/sauleiros/price-explorer.svg?style=for-the-badge

[license-url]: https://github.com/SaulEiros/price-explorer/blob/main/LICENSE

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/saul-eiros/

[springboot]: https://img.shields.io/badge/spring_boot-000000?style=for-the-badge&logo=springboot&logoColor=white&color=%236DB33F

[springboot-url]: https://spring.io/projects/spring-boot

[java]: https://img.shields.io/badge/Java_21-000000?style=for-the-badge&color=FF9E0F

[java-url]: https://docs.oracle.com/en/java/javase/21/

[junit]: https://img.shields.io/badge/JUnit%205-000000?style=for-the-badge&logo=junit5&logoColor=white&color=%2325A162

[junit-url]: https://junit.org/junit5/

[mockito]: https://img.shields.io/badge/Mockito-000000?style=for-the-badge&logoColor=white&color=%23FF6633

[mockito-url]: https://site.mockito.org/

[swagger]: https://img.shields.io/badge/Swagger-000000?style=for-the-badge&logo=swagger&logoColor=white&color=%2383B81A

[swagger-url]: https://swagger.io/