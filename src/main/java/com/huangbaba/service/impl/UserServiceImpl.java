package com.huangbaba.service.impl;

import com.huangbaba.mapper.UserMapper;
import com.huangbaba.pojo.Users;
import com.huangbaba.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Auther:huangbaba
 * @Date: 2021/10/14 - 10 - 14 - 19:43
 * @DEscription: com.huangbaba.service.impl
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Users findUser(String name) {
        return userMapper.findUser(name);
    }
    @Override
    public List<Users> findAllUser() {
        return userMapper.findAllUser();
    }
}
