package com.service.pricecomparision.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Document("product")
@Data
public class ProductDB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String productId;

    private String providerId;

    private String providerName;

    private String productName;

    private String categoryId;

    private String categoryName;

    private String description;

    private Double price;

    private Double rank;

}
