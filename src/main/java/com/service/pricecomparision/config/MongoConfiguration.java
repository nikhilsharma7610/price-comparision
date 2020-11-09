package com.service.pricecomparision.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.service.pricecomparision.constant.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Configuration
public class MongoConfiguration {

    @Value("${mongodb.url}")
    private String mongoConnStr;

    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoConnStr);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), AppConstant.MongoDB.PRICE_COMPARISON);
    }

}
