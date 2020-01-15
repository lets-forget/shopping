package com.ning.home_admin.sytem.mapper;

import com.ning.home_admin.sytem.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    int updatecheckCode(String code);

    int updateImage(@Param("userId") Integer id, @Param("avatar") String toString);

    int updatePassword(@Param("userId") Integer id, @Param("password") String newPs);

    User selectIsByEmail(String email);

    int updateByUsernameAndPassword(@Param("username") String username, @Param("password") String newPs);

    List<User> selectIsByEmailList(String email);
}