package com.service.pricecomparision.requests;

import com.service.pricecomparision.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest extends BaseProductRequest {

    @NotBlank(message = AppConstant.ValidationErrors.NOT_BLANK)
    private String providerName;
    @NotBlank(message = AppConstant.ValidationErrors.NOT_BLANK)
    private String categoryName;

}
