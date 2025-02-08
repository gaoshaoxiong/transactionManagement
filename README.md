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
![image](https://github.com/user-attachments/assets/bc5064af-8129-447f-b7d1-400e1010e83f)
![image](https://github.com/user-attachments/assets/b3b495ec-0519-489a-a61f-0344eb503184)
#### 1.2 Add a invalid parameter transaction
##### 1.2.1 transaction subject is not blank
![img_3](https://github.com/user-attachments/assets/0452d5c6-77be-4b49-bb4e-66d462dbfd60)
![img_4](https://github.com/user-attachments/assets/f8c8731e-a98f-44e1-9059-2487305d673a)

##### 1.2.2 Invalid transaction type enum value
![img_5](https://github.com/user-attachments/assets/48065717-da03-401e-83e5-f6d7115bbc47)
![img_6](https://github.com/user-attachments/assets/c4d768b9-ca69-433d-8ad8-b6b5d2ecaf21)

##### 1.2.3 amount must be greater than 0.01
![img_7](https://github.com/user-attachments/assets/bb9e1483-e0d7-4566-9c7e-ecdff1b447c4)
![img_8](https://github.com/user-attachments/assets/5eb2706a-dce0-4cf9-a491-66c5c23d6911)

#### 1.3 Add a duplicate transaction
![img_9](https://github.com/user-attachments/assets/6a9d5312-328e-4d3b-aa40-466d30bc5764)
![img_10](https://github.com/user-attachments/assets/a23a6238-0686-4602-b8f2-ac7e3863cf97)

#### 1.4 Modify a transaction
![img_11](https://github.com/user-attachments/assets/7281dc6e-a16d-468b-9ce3-31482c27c3e4)
![img_12](https://github.com/user-attachments/assets/c458cac5-1177-473b-a91e-59f9bf5ee343)

#### 1.5 Modify a non-exist transaction
![img_13](https://github.com/user-attachments/assets/1990b4c4-a936-4d62-b8cf-a93037e5f239)
![img_14](https://github.com/user-attachments/assets/f26d01a3-3b35-4c99-84ff-1b22d5a54988)

#### 1.6 Delete a transaction
![img_15](https://github.com/user-attachments/assets/7318dcdb-8ebc-488a-9cbb-b2262e97d9cb)
![img_16](https://github.com/user-attachments/assets/64f8b25a-fd56-479b-bdc1-e9783fe7204c)

#### 1.7 Query not deleted transaction by pagination
![img_17](https://github.com/user-attachments/assets/d876d812-6c03-4958-b3a8-9f57e2f8de7f)
![img_18](https://github.com/user-attachments/assets/6dbdd43c-0958-45c7-a1a3-22f508cef436)

#### 1.8 Query revise transaction records
![img_19](https://github.com/user-attachments/assets/3568d5a4-1cbd-4e8e-a60b-59809321fdb5)
![img_20](https://github.com/user-attachments/assets/0c9603ae-3b5c-4bd0-b0c7-b02fbf15fdeb)


#### 2. Use Web Page

http://localhost:8080/index.html

#### 2.1 Add a transaction
![img_21](https://github.com/user-attachments/assets/5c4122c5-9da5-41d6-9371-95d20183beac)

#### 2.2 Edit a transaction
![img_22](https://github.com/user-attachments/assets/2aaba4ab-e2eb-4af9-b1fc-f3aedd6aa260)

#### 2.3 Delete a transaction
![img_23](https://github.com/user-attachments/assets/250b6030-d2e2-43a6-b50f-93afcabc7e7b)

#### 2.4 Query not deleted transaction by pagination
![img_24](https://github.com/user-attachments/assets/80f1c028-02e7-42c2-acd7-0251bd7ca961)

#### 2.5 Invalid parameter
![img_25](https://github.com/user-attachments/assets/f2cc51b1-37d1-4073-82c6-ad0476a10df8)


### Testing
#### TransactionControllerTest.class
This test suite validates the functionality of the Transaction Management API endpoints using Spring Boot's testing framework.All tests passed.
![img_29](https://github.com/user-attachments/assets/fb009de8-e07b-47ed-aa73-e935289cdefe)


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
![img_26](https://github.com/user-attachments/assets/c2733282-7f7f-42ab-a737-d205e9b185be)
![img_27](https://github.com/user-attachments/assets/30641f3b-4fe1-4f4b-9e4f-eb742a72e574)
![img_28](https://github.com/user-attachments/assets/fcaa7c16-67d6-4b3c-90b2-a2894deab036)


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
