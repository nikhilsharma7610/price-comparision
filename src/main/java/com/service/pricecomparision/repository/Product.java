package com.service.pricecomparision.repository;

import com.service.pricecomparision.constant.AppConstant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author nikhil sharma created on 07/11/20
 */
@Document(indexName = AppConstant.IndexName.PRODUCT)
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String productId;

    @Field(type = FieldType.Keyword)
    private String providerId;

    @Field(type = FieldType.Text)
    private String providerName;

    @Field(type = FieldType.Text)
    private String productName;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(type = FieldType.Text)
    private String categoryName;

    @Field(type = FieldType.Keyword)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Double)
    private Double rank;

}
