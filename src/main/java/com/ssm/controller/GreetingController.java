package com.ssm.controller;

import com.ssm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/users")
    public String users(Map<String, Object> context) {
        try {
            context.put("users", userService.selectByParams(new HashMap<String, Object>()));
        } catch (Exception e) {
            LOG.error("users exception", e);
        }
        return "users";
    }

}
