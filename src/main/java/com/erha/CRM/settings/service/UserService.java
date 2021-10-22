package com.erha.CRM.settings.service;

import com.erha.CRM.settings.domain.User;

import java.util.List;

public interface UserService {

    List<User> queryAll();

    User queryByLogin(String userName,String pwd,String ip);

    User queryById(String id);
}
