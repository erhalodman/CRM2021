package com.erha.CRM.settings.service.impl;

import com.erha.CRM.settings.dao.UserMapper;
import com.erha.CRM.settings.domain.User;
import com.erha.CRM.settings.service.UserService;
import com.erha.CRM.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassNameUserServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/921:59
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> queryAll(){
        return userMapper.selectAll();
    }

    /**
     * sql查询的user对象是否为空 :用户名或密码错误
     * expireTime 验证失效时间
     * lockState 验证锁定状态
     * allowIps 验证id是否允许访问
     **/
    @Override
    public User queryByLogin(String userName,String pwd,String ip){
        User user = userMapper.selectByLogin(userName, pwd);
        if(user == null){
            throw new RuntimeException("账号或密码错误");
        }
        if(user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0){
            throw new RuntimeException("账号已失效");
        }
        if("0".equals(user.getLockState())){
            throw new RuntimeException("账号已锁定");
        }
        if(!user.getAllowIps().contains(ip)){
            throw new RuntimeException("访问地址无访问权限");
        }
        return user;
    }

    @Override
    public User queryById(String id) {
        return userMapper.selectById(id);
    }
}
