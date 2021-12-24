package com.huangbaba.mapper;

import com.huangbaba.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/10/14 - 10 - 14 - 19:43
 * @DEscription: com.huangbaba.mapper
 * @version: 1.0
 */
@Mapper
public interface UserMapper {
    Users findUser(String name);
    List<Users> findAllUser();
}
