package com.service.pricecomparision.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nikhil sharma created on 10/11/20
 */
@RestController
public class PingController {

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "OK";
    }

}
