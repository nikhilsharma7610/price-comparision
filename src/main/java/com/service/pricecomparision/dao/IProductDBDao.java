package com.service.pricecomparision.dao;

import com.service.pricecomparision.repository.ProductDB;

/**
 * @author nikhil sharma created on 08/11/20
 */
public interface IProductDBDao {

    void save(ProductDB productDB) throws Exception;

    ProductDB updatePriceByProductNameProviderIdCategoryId(String productName, String providerId,
                                                           String categoryId, double price);

    ProductDB updateRankByProductNameProviderIdCategoryId(String productName, String providerId,
                                                          String categoryId, double price);

    ProductDB getProductById(String productId);

    ProductDB findByProductNameProviderIdCategoryId(String productName, String providerId, String categoryId);

    double findMaxPriceByCategory(String categoryId);

    double findMinPriceByCategory(String categoryId);

    void removeAllDocuments();
}
