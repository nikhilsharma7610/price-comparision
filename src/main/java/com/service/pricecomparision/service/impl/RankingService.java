package com.service.pricecomparision.service.impl;

import com.service.pricecomparision.constant.AppConstant;
import com.service.pricecomparision.dao.impl.ProductDBDao;
import com.service.pricecomparision.enums.SortingOptions;
import com.service.pricecomparision.repository.Product;
import com.service.pricecomparision.repository.ProductDB;
import com.service.pricecomparision.service.IRankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Service
public class RankingService implements IRankingService {

    private static final Logger LOG = LoggerFactory.getLogger(RankingService.class);

    @Autowired
    private ProductDBDao productRepo;

    @Override
    public List<Product> rankProducts(List<Product> products, SortingOptions sortingOption) {

        List<Product> rankedProducts = null;
        if (CollectionUtils.isEmpty(products))
            return rankedProducts;

        LOG.info("Sorting products by sortingOption : {}", sortingOption);
        switch (sortingOption) {

            case DEFAULT:
                products.sort( (Product p1, Product p2) -> sortByProduct(p1, p2) );
                rankedProducts = products;
                break;
        }

        LOG.info("Product after sorting, rankedProducts : {}", rankedProducts);
        return rankedProducts;
    }

    @Override
    public void updateProductRank(String productId) {

        try {
            // calculate new rank
            ProductDB productDB = productRepo.getProductById(productId);
            double rank = calculateRank(productDB);

            // update rank in db
            createProductDBObject(productDB, rank);

        } catch (Exception e) {
            LOG.error("Exception is updating product rank, message : {}", e.getMessage(), e);
        }
    }

    private double calculateRank(ProductDB productDB) {

        // find Max price
        double maxPrice = productRepo.findMaxPriceByCategory(productDB.getCategoryId());
        double minPrice = productRepo.findMinPriceByCategory(productDB.getCategoryId());

        double rank = 0D;

        // if >max price, rank is 100
        if (Double.compare(productDB.getPrice(), maxPrice) >= 0) {
            rank = AppConstant.RankBoundaries.MAX;
        }
        // if <min price, rank is 1
        else if (Double.compare(productDB.getPrice(), minPrice) <= 0) {
            rank = AppConstant.RankBoundaries.MIN;
        }
        else {
            // find where it gets presents in between min & max price
            rank = (maxPrice - minPrice) * productDB.getPrice() + minPrice;
            BigDecimal bd = new BigDecimal(rank).setScale(2, RoundingMode.HALF_UP);
            rank = bd.doubleValue();
        }

        // assign rank
        return rank;
    }

    private ProductDB createProductDBObject(ProductDB productDB, double rank) {

        return productRepo.updateRankByProductNameProviderIdCategoryId(
                productDB.getProductName(),
                productDB.getProviderId(),
                productDB.getCategoryId(),
                rank
        );
    }

    public int sortByProduct(Product product1, Product product2) {

        if (product1 == null && product2 == null)
            return 0;
        else if (product1 == null)
            return -1;
        else if (product2 == null)
            return 1;

        return sortByRank(product1.getRank(), product2.getRank());
    }

    public int sortByRank(Double rank1, Double rank2) {

        if (rank1 == null && rank2 == null)
            return 0;
        else if (rank1 == null)
            return -1;
        else if (rank2 == null)
            return 1;

        return Double.compare(rank1, rank2);
    }

}
