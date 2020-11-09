package com.service.pricecomparision.dao;

import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.repository.Product;

import java.util.List;

/**
 * @author nikhil sharma created on 07/11/20
 */
public interface IProductESDao {

    List<Product> searchProducts(SearchRequest searchRequest);

    void save(Product product) throws Exception;

    void createIndex(String indexName);

    void deleteIndex(String indexName);
}
