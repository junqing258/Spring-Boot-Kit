package org.inlighting.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.inlighting.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from users where user_id=#{id}")
    UserEntity selectUserById(Integer id);

    @Select("select * from users where username=#{username}")
    UserEntity selectUserByName(String username);

    @Insert("INSERT INTO users (username, password, role, permission) VALUES (#{username}, #{password}, #{role}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "user_id", keyColumn = "user_id")
    Long insertUser(UserEntity user);

}