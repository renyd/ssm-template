package com.ssm.service.impl;

import com.ssm.mapper.UserMapper;
import com.ssm.mapper.UserRoleMapper;
import com.ssm.model.User;
import com.ssm.model.UserRole;
import com.ssm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Domg on 2017/1/15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional("transactionManager")
    public void addUserRole() {
        Date now = new Date();
        User u = new User();
        u.setName("green");
        u.setCreateTime(now);
        u.setUpdateTime(now);
        UserRole userRole = new UserRole();
        userRole.setUserId(1L);
        userRole.setRoleId(1L);
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        userMapper.insert(u);
        userRoleMapper.insert(userRole);
    }
}
