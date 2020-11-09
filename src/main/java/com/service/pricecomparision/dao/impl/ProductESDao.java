package com.service.pricecomparision.dao.impl;

import com.service.pricecomparision.constant.AppConstant;
import com.service.pricecomparision.dao.IProductESDao;
import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.repository.Product;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.service.pricecomparision.constant.AppConstant.ProductFields.CATEGORY_ID;
import static com.service.pricecomparision.constant.AppConstant.ProductFields.PRODUCT_NAME;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author nikhil sharma created on 07/11/20
 */
@Repository
public class ProductESDao implements IProductESDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductESDao.class);

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    private  ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Product> searchProducts(SearchRequest searchRequest) {

        List<Product> products = null;
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery(CATEGORY_ID, searchRequest.getCategory()))
                .withQuery(matchQuery(PRODUCT_NAME, searchRequest.getProductName()))
                .build();
        SearchHits<Product> searchHits = elasticsearchOperations
                .search(searchQuery, Product.class, IndexCoordinates.of(AppConstant.IndexName.PRODUCT));

        if (searchHits != null && !CollectionUtils.isEmpty(searchHits.getSearchHits())) {
            products = new ArrayList<>();
            for (SearchHit<Product> searchHit : searchHits.getSearchHits()) {
                products.add(searchHit.getContent());
            }
        }

        return products;
    }

    @Override
    public void save(Product product) throws Exception {

        elasticsearchOperations.save(product);

    }

    @Override
    public void createIndex(String indexName) {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        try {
            highLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Error in executing pre-configuration for tests, message : {}", e.getMessage(), e);
        }

    }

    @Override
    public void deleteIndex(String indexName) {

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        try {
            highLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Error in executing pre-configuration for tests, message : {}", e.getMessage(), e);
        }

    }

}
