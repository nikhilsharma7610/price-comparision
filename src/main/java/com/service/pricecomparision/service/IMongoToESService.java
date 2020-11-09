package com.service.pricecomparision.service;

/**
 * @author nikhil sharma created on 08/11/20
 */
public interface IMongoToESService {

    void save(String productId);

    void waitToSyncMongoToEs() throws InterruptedException;
}
