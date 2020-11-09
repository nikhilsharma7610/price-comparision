package com.service.pricecomparision.dao;

import com.service.pricecomparision.repository.ProductDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Repository
public interface ProductDBRepository extends MongoRepository<ProductDB, String> {

}
