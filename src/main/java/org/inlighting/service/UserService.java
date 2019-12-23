package org.inlighting.service;

import org.inlighting.entity.UserEntity;

public interface UserService {

    UserEntity getUserById(Integer id);


    UserEntity getUserByName(String username);
    /**
     * 保存用户
     */
    Long saveUser(UserEntity user);
}
