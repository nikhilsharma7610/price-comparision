package com.service.pricecomparision.service;

import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.repository.Product;

import java.util.List;

/**
 * @author nikhil sharma created on 07/11/20
 */
public interface ISearchService {

    List<Product> search(SearchRequest searchRequest);

}
