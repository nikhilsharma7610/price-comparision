package com.service.pricecomparision.constant;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author nikhil sharma created on 07/11/20
 */
public class AppConstant {

    public static class IndexName {
        public static final String PRODUCT = "product";
    }

    public static class MongoDB {
        public static final String PRICE_COMPARISON = "pricecomparision";
    }

    public static class ProductFields {
        public static final String CATEGORY_ID = "categoryId";
        public static final String PRODUCT_NAME = "productName";
        public static final String PROVIDER_ID = "providerId";
        public static final String PRICE = "price";
        public static final String RANK = "rank";
    }

    public static class RankBoundaries {
        public static final double MIN = 1D;
        public static final double MAX = 100D;
    }

    public static class ValidationErrors {
        public static final String NOT_BLANK = "Cannot be blank";
        public static final String NOT_NULL = "Cannot be null";
    }

    public static final ObjectMapper MAPPER = new ObjectMapper();

}
