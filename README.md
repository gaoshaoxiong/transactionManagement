# Transaction Management System

A web service built with Spring Boot + EasyUI that provides transaction management capabilities.

## Overview

This project implements a transaction management system that allows users to create, modify, delete, list, and pagination query transactions through RESTful APIs or Web page.

## Features

- Add new transactions
- Modify existing transactions
- Delete transactions
- Query transactions by conditions or pagination
- Error handling for duplicate transactions , non-existent transactions, Parameter verification, Custom Annotations verification

## Technologies

- Java
- Spring Boot
- Spring Web
- RESTful APIs
- Easy UI

## API Endpoints

### Transaction Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/transaction` | Add a new transaction |
| PUT | `/transaction/{orderNo}` | Modify an existing transaction |
| DELETE | `/transaction/{orderNo}` | Delete a transaction |
| GET | `/transaction/list` | query transactions by conditions or pagination |
| GET | `/transaction/revise/record` | query revised transactions record|

## API Response Codes

| Status Code | Description | Example |
|-------------|-------------|-------------|
| 200 | Success | /
| 400 | Bad Request - Invalid input parameters | amount must be greater than 0.01
| 500 | Internal Server Error/ Bussiness Logic Error |Duplicate transaction


## Getting Started

### Prerequisites

- Java JDK 21
- Maven
- Your favorite IDE (IntelliJ IDEA, Eclipse, etc.)

### Start

1. Clone the repository
2. Use IntelliJ IDEA open this project as a maven project
3. Start the project

### How to use

#### 1. Use Swagger UI to test API

http://localhost:8080/swagger-ui/index.html

#### 1.1 Add a new transaction
![img_1.png](img_1.png)
![img_2.png](img_2.png)
#### 1.2 Add a invalid parameter transaction
##### 1.2.1 transaction subject is not blank
![img_3.png](img_3.png)
![img_4.png](img_4.png)
##### 1.2.2 Invalid transaction type enum value
![img_5.png](img_5.png)
![img_6.png](img_6.png)
##### 1.2.3 amount must be greater than 0.01
![img_7.png](img_7.png)
![img_8.png](img_8.png)
#### 1.3 Add a duplicate transaction
![img_9.png](img_9.png)
![img_10.png](img_10.png)
#### 1.4 Modify a transaction
![img_11.png](img_11.png)
![img_12.png](img_12.png)
#### 1.5 Modify a non-exist transaction
![img_13.png](img_13.png)
![img_14.png](img_14.png)
#### 1.6 Delete a transaction
![img_15.png](img_15.png)
![img_16.png](img_16.png)
#### 1.7 Query not deleted transaction by pagination
![img_17.png](img_17.png)
![img_18.png](img_18.png)
#### 1.8 Query revise transaction records
![img_19.png](img_19.png)
![img_20.png](img_20.png)

#### 2. Use Web Page

http://localhost:8080/index.html

#### 2.1 Add a transaction
![img_21.png](img_21.png)
#### 2.2 Edit a transaction
![img_22.png](img_22.png)
#### 2.3 Delete a transaction
![img_23.png](img_23.png)
#### 2.4 Query not deleted transaction by pagination
![img_24.png](img_24.png)
#### 2.5 Invalid parameter
![img_25.png](img_25.png)

### Testing
#### TransactionControllerTest.class
This test suite validates the functionality of the Transaction Management API endpoints using Spring Boot's testing framework.All tests passed.
![img_29.png](img_29.png)

#### Performance Test
This load test suite evaluates the performance and reliability of the Transaction Management API under concurrent load conditions using Spring Boot's testing framework.
##### Test Configuration
- **Test Environment**: Windows x64 4C 16G
- **Client**: Jmeter 5.6.3
- **Concurrency**: Multi-threaded execution
##### Load Test Parameters
- Number of Threads: 10000
- Requests per Thread: 1
- Total Requests: 10000
- Timeout: 1 minutes
- Success Rate Threshold: 100%
##### Test Scenario: Transaction Creation
The test creates multiple transactions concurrently with the following characteristics:
- Fixed type (DEPOSIT)
- Fixed subject (TEST-A)
- Fixed object (TEST-B)
- Fixed amount (1)
- Fixed remark (TEST add a new transaction)
  
- Concurrent POST requests to `/transaction`
##### Sample Output
![img_26.png](img_26.png)
![img_27.png](img_27.png)
![img_28.png](img_28.png)

### External Libraires outside the standard JDK
##### 1. org.projectlombok.lombok
Used to generate getter and setter methods for the Transaction class.
##### 2. org.springframework.boot.spring-boot-starter-validation
Used to validate the input parameters of the Transaction Management API endpoints.
##### 3. org.apache.maven.plugins.maven-assembly-plugin
Used to package the progject
#### 4. org.springdoc.springdoc-openapi-starter-webmvc-ui
Used to surpport springdoc-openaip.So we can view and test APIs on browser
#### 5. org.springframework.boot.spring-boot-starter-test
Used to support testing

#### 1.  Dockerfile
We can use a Dockerfile to create a Docker image and upload the image to Docker Hub.