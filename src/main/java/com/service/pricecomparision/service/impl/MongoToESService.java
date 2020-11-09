package com.service.pricecomparision.service.impl;

import com.service.pricecomparision.dao.IProductDBDao;
import com.service.pricecomparision.dao.IProductESDao;
import com.service.pricecomparision.repository.Product;
import com.service.pricecomparision.repository.ProductDB;
import com.service.pricecomparision.service.IMongoToESService;
import com.service.pricecomparision.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Service
public class MongoToESService implements IMongoToESService {

    private static final Logger LOG = LoggerFactory.getLogger(MongoToESService.class);

    private static ConcurrentLinkedDeque<String> QUEUE = new ConcurrentLinkedDeque<>();
    private volatile boolean process = true;

    @Value("${mongo.es.sync.interval.ms}")
    private long mongoEsSyncInterval;

    @Autowired
    private IProductDBDao productDBDao;

    @PostConstruct
    public void init() {

        CompletableFuture.supplyAsync(() -> {
            process();
            return true;
        });

    }

    @Autowired
    private IProductESDao productDao;

    private void saveInES(String productId) {

        LOG.info("Saving in ES, productId : {}", productId);
        try {
            // save in ES
            ProductDB productDB = productDBDao.getProductById(productId);
            Product productES = MapperUtils.toObject(productDB, Product.class);
            productDao.save(productES);

        } catch (Exception e) {
            LOG.error("Exception is saving product in ES, message : {}", e.getMessage(), e);
        }
    }

    private void process() {

        try {
            while (process) {
                if (QUEUE != null && !QUEUE.isEmpty()) {
                    String productId = QUEUE.poll();
                    saveInES(productId);
                }

                waitToSyncMongoToEs();
            }
        }
        catch (Exception e) {
            LOG.error("Exception is polling from queue, exisitng polling loop, message : {}",
                    e.getMessage(), e);
        }
    }

    public void stopProcessing() {
        this.process = false;
    }

    public void resumeProcessing() {
        this.process = true;
    }

    @Override
    public void save(String productId) {
        QUEUE.offer(productId);
    }

    @Override
    public void waitToSyncMongoToEs() throws InterruptedException {
        if (mongoEsSyncInterval <= 0)
            return;
        Thread.sleep(mongoEsSyncInterval);
    }

}
