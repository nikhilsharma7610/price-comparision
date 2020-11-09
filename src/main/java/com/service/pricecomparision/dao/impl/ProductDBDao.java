package com.service.pricecomparision.dao.impl;

import com.service.pricecomparision.dao.IProductDBDao;
import com.service.pricecomparision.dao.ProductDBRepository;
import com.service.pricecomparision.repository.ProductDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static com.service.pricecomparision.constant.AppConstant.ProductFields.*;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Repository
public class ProductDBDao implements IProductDBDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDBDao.class);

    @Autowired
    private ProductDBRepository productRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ProductDB productDB) throws Exception {

        productRepo.save(productDB);

    }

    @Override
    public ProductDB updatePriceByProductNameProviderIdCategoryId(String productName, String providerId,
                                                                  String categoryId, double price) {

        Query query = getQueryForProductNameProviderIdCategoryId(productName, providerId, categoryId);
        Update update = new Update();
        update.set(PRICE, price);
        return mongoTemplate.findAndModify(query, update, ProductDB.class);
    }

    @Override
    public ProductDB updateRankByProductNameProviderIdCategoryId(String productName, String providerId,
                                                                 String categoryId, double rank) {

        Query query = getQueryForProductNameProviderIdCategoryId(productName, providerId, categoryId);
        Update update = new Update();
        update.set(RANK, rank);
        return mongoTemplate.findAndModify(query, update, ProductDB.class);
    }

    @Override
    public ProductDB getProductById(String productId) {
        return mongoTemplate.findById(productId, ProductDB.class);
    }

    @Override
    public ProductDB findByProductNameProviderIdCategoryId(String productName, String providerId, String categoryId) {

        Query query = getQueryForProductNameProviderIdCategoryId(productName, providerId, categoryId);
        return mongoTemplate.findOne(query, ProductDB.class);
    }

    @Override
    public double findMaxPriceByCategory(String categoryId) {

        return findMaxMinPriceByCategory(categoryId, Sort.Direction.DESC);
    }

    @Override
    public double findMinPriceByCategory(String categoryId) {

        return findMaxMinPriceByCategory(categoryId, Sort.Direction.ASC);
    }

    private double findMaxMinPriceByCategory(String categoryId, Sort.Direction direction) {

        Query query = getCategoryIdQuery(categoryId);
        query.fields().include(PRICE);
        query.with(PageRequest.of(0, 1));
        query.with(Sort.by(direction, PRICE));

        ProductDB productDB = mongoTemplate.findOne(query, ProductDB.class);
        return productDB.getPrice() == null ? 0D : productDB.getPrice();
    }

    private Query getQueryForProductNameProviderIdCategoryId(ProductDB productDB) {

        return getQueryForProductNameProviderIdCategoryId(productDB.getProductName(),
                productDB.getProviderId(),
                productDB.getCategoryId());
    }

    private Query getCategoryIdQuery(String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CATEGORY_ID).is(categoryId));
        return query;
    }

    private Query getQueryForProductNameProviderIdCategoryId(String productName, String providerId, String categoryId) {

        Query query = new Query();
        query.addCriteria(Criteria.where(PRODUCT_NAME).is(productName));
        query.addCriteria(Criteria.where(PROVIDER_ID).is(providerId));
        query.addCriteria(Criteria.where(CATEGORY_ID).is(categoryId));

        return query;
    }

    @Override
    public void removeAllDocuments() {
        mongoTemplate.remove(new Query(), ProductDB.class);
    }

}
