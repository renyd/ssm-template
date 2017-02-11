package com.ssm.service;

import com.ssm.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Domg on 2017/1/15.
 */
public interface UserService {

    List<User> selectByParams(Map<String,Object> params);

    void addUserRole();
}
