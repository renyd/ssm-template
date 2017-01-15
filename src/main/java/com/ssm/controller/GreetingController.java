package com.ssm.controller;

import com.ssm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Domg on 2017/1/2.
 */
@Controller
@RequestMapping("/greeting")
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/hello")
    public String hello() {
        LOG.info("invoke hello!");
        return "hello";
    }

    @RequestMapping("/testTx")
    public String testTx() {
        userService.addUserRole();
        return "hello";
    }

}
