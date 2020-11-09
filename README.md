## Overview

Assignment project to show products best matching to user out of possible options

## Tech & Version
- ES : 7.9.3
- Mongo : 4.4.1
- Spring boot : 2
- Java : 8
- Maven : 3.6.1
- Kibana : 7.9.3
- Other version can be checked via : mvn dependency:tree

## Requirements
- Java installed & configured
- MongoDB installed
    -  running on local on port : 27017
- ElasticSearch installed
    -  running on local on port : 9200
- Kibana installed
    - running on local on port : 5601
    
## Compiling
- run command
 
mvn clean install -DskipTests

## Running Tests
- run command

mvn clean install -DskipTests

## Storage Details
- ES 
    - clustern name : elasticsearch
    -  index name : product
- MongoDB
    - database name : pricecomparision
    - collection name : product
    
## Queries to view data
- MongoDB
    -  run commands
        - use pricecomparision
        - show collections
        - db.product.find().pretty()
- ES
    - run commands
        - cd [kibana installation directory]/bin
        - ./kibana
        - open in browser : http://localhost:5601/app/dev_tools#/console
        - GET /_cluster/health
        - GET /product/_search

## Run application
- run command
    - mvn spring-boot:run
- Check application running via ping url
    - curl --request GET 'http://localhost:8080/ping'

## Sample Requests
- Add Product
    - curl --location --request POST 'http://localhost:8080/partner/v1/product' \
       --header 'Content-Type: application/json' \
       --data-raw '{
           "providerId" : "P1",
           "providerName" : "Partner1",
           "productName" : "Fruity",
           "categoryId" : "FNB",
           "categoryName" : "Food & Drinks",
           "price" : 10.0
       }'
     - Params description
        - productName : mendatory
        - providerId : mendatory
        - providerName : mendatory
        - categoryId : mendatory
        - categoryName : mendatory
        - price : mendatory
- Update Product
    - curl --location --request PUT 'http://localhost:8080/partner/v1/product' \
      --header 'Content-Type: application/json' \
      --data-raw '{
          "providerId" : "P1",
          "providerName" : "Partner1",
          "productName" : "Fruity",
          "categoryId" : "FNB",
          "categoryName" : "Food & Drinks",
          "price" : 12.0
      }'
    - Params description
      - productName : mendatory
      - providerId : mendatory
      - categoryId : mendatory
      - price : mendatory
- Search Products
    - curl --request GET 'http://localhost:8080/v1/search?category=FNB&productName=fruity'
    - Sample response
        - [
              {
                  "productId": "21ec5690-c74a-471f-9f46-8f1a37e25e49",
                  "providerId": "P1",
                  "providerName": "Partner1",
                  "productName": "Fruity",
                  "categoryId": "FNB",
                  "categoryName": "Food & Drinks",
                  "description": null,
                  "price": 5.0,
                  "rank": 1.0
              },
              {
                  "productId": "58449dc2-1b05-4f01-b11a-8a0572ad6682",
                  "providerId": "P2",
                  "providerName": "Partner1",
                  "productName": "Fruity",
                  "categoryId": "FNB",
                  "categoryName": "Food & Drinks",
                  "description": null,
                  "price": 5.0,
                  "rank": 1.0
              },
              {
                  "productId": "6363e9c7-ca30-49ed-b8f2-81c74792e468",
                  "providerId": "P3",
                  "providerName": "Partner2",
                  "productName": "Fruity",
                  "categoryId": "FNB",
                  "categoryName": "Food & Drinks",
                  "description": null,
                  "price": 10.0,
                  "rank": 75.0
              }
          ]  

## To Do / Improvements
- Updating rank on getting price update is done for the recipient product only, 
already max & min price products need to be updated as well in relative to recipient product
- Used In-memory Queue in MongoToESService class. In production scenario will use streaming / queuing solution
- Instead of ES Product object, trimmed Product object to be sent in response

References
- [Create Index API] (https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-create-index.html)
- [Delete Index API] (https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-delete-index.html)
- [Put Mapping API] (https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-put-mapping.html)
- [Search API] (https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html)
- [Spring Mongo] (https://www.baeldung.com/spring-data-mongodb-tutorial)
- [Spring ES] (https://www.baeldung.com/spring-data-elasticsearch-tutorial)
- [Spring ES Queries] (https://www.baeldung.com/spring-data-elasticsearch-queries)