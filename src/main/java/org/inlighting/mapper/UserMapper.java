package org.inlighting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.inlighting.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    UserEntity selectUserById(Long id);

    UserEntity selectUserByName(String username);

    Long insertUser(UserEntity user);
}