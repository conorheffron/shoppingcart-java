# shoppingcart-java

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

[![Java CI with Gradle](https://github.com/conorheffron/shoopingcart-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/conorheffron/shoppingcart-java/actions/workflows/gradle.yml)

[![SonarQube](https://github.com/conorheffron/shoppingcart-java/actions/workflows/sonarcloud.yml/badge.svg)](https://github.com/conorheffron/shoppingcart-java/actions/workflows/sonarcloud.yml)

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=conorheffron_shoppingcart-java)](https://sonarcloud.io/summary/new_code?id=conorheffron_shoppingcart-java)

[SonarQube Overall Code Summary](https://sonarcloud.io/summary/overall?id=conorheffron_shoppingcart-java&branch=main)

<img width="2474" height="1117" alt="image" src="https://github.com/user-attachments/assets/986d48cd-8c20-4fc2-b5bb-f272d6f72296" />

## Tech:
 - Java 21 (LTS), Maven 3.9, Gradle 8.1, IntelliJ IDEA 2025.2.1 (Community Edition), SonarQube, JaCoCo

## Test Data
 - [https://conorheffron.github.io/shopping-cart-test-data/products/](https://github.com/conorheffron/shopping-cart-test-data/tree/main/products)

## Maven Build
```
./gradlew clean build test
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
