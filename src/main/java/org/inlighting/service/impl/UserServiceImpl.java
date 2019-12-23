package org.inlighting.service.impl;

import org.inlighting.entity.UserEntity;
import org.inlighting.mapper.UserMapper;
import org.inlighting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserEntity getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    @Override
    public Long saveUser(UserEntity user) {
        return userMapper.insertUser(user);
    }
}
