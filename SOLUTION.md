# shoppingcart-java

[![Java CI with Gradle](https://github.com/conorheffron/shoopingcart-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/conorheffron/shoppingcart-java/actions/workflows/gradle.yml)

[![SonarQube](https://github.com/conorheffron/shoppingcart-java/actions/workflows/sonarcloud.yml/badge.svg)](https://github.com/conorheffron/shoppingcart-java/actions/workflows/sonarcloud.yml)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=conorheffron_shoppingcart-java)](https://sonarcloud.io/summary/new_code?id=conorheffron_shoppingcart-java)

[SonarQube Overall Code Summary](https://sonarcloud.io/summary/overall?id=conorheffron_shoppingcart-java&branch=main)

## Tech:
 - Java 21 (LTS), Maven 3.9.9, Gradle 8.1, IntelliJ IDEA 2025.2.1 (Community Edition), SonarQube, JaCoCo

## Maven Build
```
shoppingcart % ./gradlew clean build test
```
![maven-build](screenshots/maven-build.png)

## Preview Product Info by Cereal ID(S) to find associated single unit 'price'
 - Cornflakes
![product-info-cornflakes](screenshots/product-info-cornflakes.png)
 - Weetabix
![product-info-weetabix](screenshots/product-info-weetabix.png)

## Sample Test Case as JUnit test (including JSON data fetch call(s))
![simple-cart-calculation-test](screenshots/sample-cart-calculation-test.png)

## Test Coverage Report
![test-coverage](screenshots/test-coverage.png)

## Convert to Gradle Build & Run with Tests
 - `gradle init --type pom`

![gradle-build](screenshots/gradle-build.png)
