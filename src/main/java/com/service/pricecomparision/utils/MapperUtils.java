package com.service.pricecomparision.utils;

import static com.service.pricecomparision.constant.AppConstant.MAPPER;

/**
 * @author nikhil sharma created on 08/11/20
 */
public class MapperUtils {

    public static <T> T toObject(Object object, Class<T> _class) {
        return MAPPER.convertValue(object, _class);
    }

}
