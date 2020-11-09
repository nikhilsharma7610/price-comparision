package com.service.pricecomparision.requests;

import com.service.pricecomparision.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author nikhil sharma created on 08/11/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = AppConstant.ValidationErrors.NOT_BLANK)
    private String productName;
    @NotBlank(message = AppConstant.ValidationErrors.NOT_BLANK)
    private String providerId;
    @NotBlank(message = AppConstant.ValidationErrors.NOT_BLANK)
    private String categoryId;
    @NotNull(message = AppConstant.ValidationErrors.NOT_NULL)
    private Double price;
    private String description;

}
