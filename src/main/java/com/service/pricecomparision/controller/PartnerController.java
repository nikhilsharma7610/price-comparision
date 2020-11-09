package com.service.pricecomparision.controller;

import com.service.pricecomparision.requests.ProductCreateRequest;
import com.service.pricecomparision.requests.ProductUpdateRequest;
import com.service.pricecomparision.service.IPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikhil sharma created on 08/11/20
 */
@RestController
@RequestMapping("/partner")
@Validated
public class PartnerController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductSearchController.class);

    @Autowired
    private IPartnerService partnerService;

    @RequestMapping(path = "/v1/product", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {

        String message = "Saved successfully";
        try {
            partnerService.save(productCreateRequest);
        }
        catch (Exception e) {
            LOG.error("Exception in saving product, message : {}", e.getMessage(), e);
            message = e.getMessage();
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(path = "/v1/product", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@Validated @RequestBody ProductUpdateRequest productUpdateRequest) {

        String message = "Updated successfully";
        try {
            partnerService.save(productUpdateRequest);
        }
        catch (Exception e) {
            LOG.error("Exception in saving product, message : {}", e.getMessage(), e);
            message = e.getMessage();
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
