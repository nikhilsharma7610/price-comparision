package com.service.pricecomparision.service.impl;

import com.service.pricecomparision.dao.impl.ProductDBDao;
import com.service.pricecomparision.exception.ProductExistsException;
import com.service.pricecomparision.repository.ProductDB;
import com.service.pricecomparision.requests.ProductCreateRequest;
import com.service.pricecomparision.requests.ProductUpdateRequest;
import com.service.pricecomparision.service.IMongoToESService;
import com.service.pricecomparision.service.IPartnerService;
import com.service.pricecomparision.service.IRankingService;
import com.service.pricecomparision.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Service
public class PartnerService implements IPartnerService {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerService.class);

    @Autowired
    private ProductDBDao productRepo;

    @Autowired
    private IMongoToESService mongoToESService;

    @Autowired
    private IRankingService rankingService;

    @Override
    public String save(ProductCreateRequest productCreateRequest) throws Exception {

        LOG.info("Got request to create product, productCreateRequest : {}", productCreateRequest);
        try {
            ProductDB productDB = MapperUtils.toObject(productCreateRequest, ProductDB.class);
            if (productExists(productDB)) {
                throw new ProductExistsException("Product already exists with this product name, provider name, category Id");
            }

            // update in mongodb
            if (StringUtils.isEmpty(productDB.getProductId())) {
                // for new product, set its id
                productDB.setProductId(UUID.randomUUID().toString());
            }
            productRepo.save(productDB);
            rankingService.updateProductRank(productDB.getProductId());

            // send to get saved in ES
            mongoToESService.save(productDB.getProductId());

            return productDB.getProductId();
        } catch (Exception e) {
            LOG.error("Exception is saving product, message : {}", e.getMessage(), e);
            throw e;
        }

    }

    private boolean productExists(ProductDB productDB) {
        ProductDB _productDB = productRepo.findByProductNameProviderIdCategoryId(
                productDB.getProductName(), productDB.getProviderId(), productDB.getCategoryId());
        return _productDB == null ? false : true;
    }

    @Override
    public String save(ProductUpdateRequest productUpdateRequest) throws Exception {

        LOG.info("Got request to update product, productUpdateRequest : {}", productUpdateRequest);
        try {
            ProductDB productDB = createProductDBObject(productUpdateRequest);
            rankingService.updateProductRank(productDB.getProductId());

            // send to get saved in ES
            mongoToESService.save(productDB.getProductId());

            return productDB.getProductId();
        } catch (Exception e) {
            LOG.error("Exception is saving product, message : {}", e.getMessage(), e);
            throw e;
        }

    }

    private ProductDB createProductDBObject(ProductUpdateRequest productUpdateRequest) {

        return productRepo.updatePriceByProductNameProviderIdCategoryId(
                productUpdateRequest.getProductName(),
                productUpdateRequest.getProviderId(),
                productUpdateRequest.getCategoryId(),
                productUpdateRequest.getPrice()
        );
    }

}
