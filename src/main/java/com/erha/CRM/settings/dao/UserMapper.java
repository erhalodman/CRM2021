package com.erha.CRM.settings.dao;

import com.erha.CRM.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<User> selectAll();

    User selectByLogin(@Param("loginAct")String userName,@Param("loginPwd")String pwd);

    User selectById(@Param("id")String id);
}
