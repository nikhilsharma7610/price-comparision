package com.service.pricecomparision.controller;

import com.service.pricecomparision.dto.SearchRequest;
import com.service.pricecomparision.enums.SortingOptions;
import com.service.pricecomparision.repository.Product;
import com.service.pricecomparision.service.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author nikhil sharma created on 07/11/20
 */
@RestController
public class ProductSearchController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductSearchController.class);

    @Autowired
    private ISearchService searchService;

    @RequestMapping(path = "/v1/search", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getSearchResponse(@RequestParam String category,
                                            @RequestParam String productName,
                                            @RequestParam(required = false) String sortingOption) {

        List<Product> products = null;
        try {
            SearchRequest searchRequest = SearchRequest.builder()
                    .category(category)
                    .productName(productName)
                    .sortingOption(StringUtils.isEmpty(sortingOption) ?
                            SortingOptions.DEFAULT : SortingOptions.getSortingOptionByName(sortingOption))
                    .build();
            products = searchService.search(searchRequest);
        }
        catch (Exception e) {
            LOG.error("Exception is searching product, message : {}", e.getMessage(), e);
            return new ResponseEntity<>(products, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
