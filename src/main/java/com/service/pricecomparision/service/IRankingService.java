package com.service.pricecomparision.service;

import com.service.pricecomparision.enums.SortingOptions;
import com.service.pricecomparision.repository.Product;

import java.util.List;

/**
 * @author nikhil sharma created on 08/11/20
 */
public interface IRankingService {

    List<Product> rankProducts(List<Product> products, SortingOptions sortingOption);

    void updateProductRank(String productId);
}
