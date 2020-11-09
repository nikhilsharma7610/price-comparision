package com.service.pricecomparision.service;

import com.service.pricecomparision.requests.ProductCreateRequest;
import com.service.pricecomparision.requests.ProductUpdateRequest;

/**
 * @author nikhil sharma created on 08/11/20
 */
public interface IPartnerService {

    String save(ProductCreateRequest productCreateRequest) throws Exception;

    String save(ProductUpdateRequest productUpdateRequest) throws Exception;
}
