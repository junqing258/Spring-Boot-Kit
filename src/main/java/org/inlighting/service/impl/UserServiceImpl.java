package org.inlighting.service.impl;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.inlighting.entity.UserEntity;
import org.inlighting.mapper.UserMapper;
import org.inlighting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

@Service("userService")
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserEntity getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    @Override
    public Long saveUser(UserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);

        String password = user.getPassword();
        String secret = new Sha256Hash(password, salt).toHex();

        user.setPassword(secret);
        user.setSalt(salt);

        return userMapper.insertUser(user);
    }
}
