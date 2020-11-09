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