# Pizza Order Shop

This project serves api for Pizza Order shop. An end user can request Pizza, Sides, Toppings and Crust available in menu. Order from customer can contains Pizza with different combinations of Sides, Toppings and Crust. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.


### Prerequisites


```
Machine with at least 4GB of RAM, 1GHz processor, default disk storage.

Java 8 or above is mandatory.

Any Java IDE (Integrated Development Environment) like Eclipse, VsCode, Intellij Idea etc. 

```

### Installing

A step by step will tell you how to get a development environment running in Eclipse. Same
steps can be repeated for other Java IDE as well.

Clone project from below github url

```
https://github.com/ashu17188/pizza-order-shop.git
```

Import project in eclipse

```
Files -> Import -> Existing Maven Projects -> Browse the code folder -> Finish.
```

Build project

```
Right click on project -> Run As -> Maven Build -> click within Goals input box -> write 'mvn clean install' -> Apply -> Run.
```

Running Project

```
 Run class 'org.innovect.assignment.AppRunner.java'
 ```

## Running the tests

Unit Test and Integration tests have been included in project. Steps to run test classes.

To run all test unit and integration test cases.

```
Right click on project -> Run As -> Junit
```

To run specific test

```
Double click on method name and right click -> Run As -> Junit 
```

## Running the Junit test coverage

Steps to run test coverage while running Junit.

```
Right click on project -> Coverage As -> Junit.
```

##Important links:

H2-database url

```
http://localhost:8085/pizza-order-shop/h2-console
```

Swagger url

```
http://localhost:8085/pizza-order-shop/swagger-ui.html
```


## Built With

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/index.html) - Java Development Kit.
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/guides/gs/rest-service/) - Used to develop Apis.


## Authors

* **Ashutosh Shukla** - *Product Development* - [Repository](https://github.com/ashu17188)

## References

** Refactoring ** https://refactoring.guru/refactoring/

** Design patterns ** https://refactoring.guru/design-patterns

** Rest maturity model ** https://www.martinfowler.com/articles/richardsonMaturityModel.html

** Clean Code by Robert C Martin ** (cheat sheet https://github.com/getmubarak/cleancode/blob/master/Clean-Code-Cheat-Sheet-V1.3.pdf)

** Batch Rest End Points** https://www.codementor.io/blog/batch-endpoints-6olbjay1hd
	
** Javax validation** https://www.baeldung.com/javax-validation 

