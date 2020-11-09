package com.service.pricecomparision.service.impl;

import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.dao.IProductESDao;
import com.service.pricecomparision.repository.Product;
import com.service.pricecomparision.service.IRankingService;
import com.service.pricecomparision.service.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author nikhil sharma created on 07/11/20
 */
@Service
public class SearchService implements ISearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private IProductESDao productDao;

    @Autowired
    private IRankingService rankingService;

    @Override
    public List<Product> search(SearchRequest searchRequest) {

        List<Product> products = productDao.searchProducts(searchRequest);
        if (!CollectionUtils.isEmpty(products)) {
            LOG.info("products : {}", products);

            // sort products by ranking
            products = rankingService.rankProducts(products, searchRequest.getSortingOption());
        }

        return products;
    }

}
