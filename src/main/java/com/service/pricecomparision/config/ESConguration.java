package com.service.pricecomparision.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author nikhil sharma created on 07/11/20
 */
@Configuration
public class ESConguration extends AbstractElasticsearchConfiguration {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private String esPort;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esHost +":"+ esPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
