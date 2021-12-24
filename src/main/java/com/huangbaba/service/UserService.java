package com.huangbaba.service;

import com.huangbaba.pojo.Users;

import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/10/14 - 10 - 14 - 19:42
 * @DEscription: com.huangbaba.service
 * @version: 1.0
 */
public interface UserService {
    Users findUser(String name);
    public List<Users> findAllUser();
}
