package com.ssm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Domg on 2017/1/2.
 */
@Controller
@RequestMapping("/greeting")
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/hello")
    public String hello() {
        LOG.info("invoke hello!");
        return "hello";
    }

}
