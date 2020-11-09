package com.service.pricecomparision.dto;

import com.service.pricecomparision.enums.SortingOptions;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author nikhil sharma created on 07/11/20
 */
@Builder
@Data
@ToString(callSuper = true)
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String category;
    private String productName;

    private SortingOptions sortingOption;

}
