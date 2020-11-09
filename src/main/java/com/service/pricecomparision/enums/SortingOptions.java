package com.service.pricecomparision.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikhil sharma created on 08/11/20
 */
public enum SortingOptions {

    DEFAULT
    ;

    private static Map<String, SortingOptions> nameToOption = new HashMap<>();

    static {
        for (SortingOptions option : SortingOptions.values()) {
            nameToOption.put(option.name(), option);
        }
    }

    public static SortingOptions getSortingOptionByName(String name) {
        return nameToOption.get(name);
    }

}
