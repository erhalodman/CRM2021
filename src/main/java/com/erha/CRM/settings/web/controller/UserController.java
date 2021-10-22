package com.erha.CRM.settings.web.controller;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.settings.service.UserService;
import com.erha.CRM.util.MD5Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassNameUserController
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/922:07
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = {"/user"})
public class UserController {
    @Autowired
    UserService userService;

    /**
     * sql查询的user对象是否为空 :用户名或密码错误
     * expireTime 验证失效时间
     * lockState 验证锁定状态
     * allowIps 验证id是否允许访问
     **/
    @RequestMapping(value = {"/login.action"},method = RequestMethod.POST)
    @ResponseBody
    public String login(String loginAct, String loginPwd,HttpServletRequest request){
        JSONObject jsonObject= new JSONObject();
        loginPwd = MD5Util.getMD5(loginPwd);
        User user = null;
        System.out.println("------------------>>>>>>"+request.getRemoteAddr());
        try {
            user = userService.queryByLogin(loginAct,loginPwd,request.getRemoteAddr());
            jsonObject.put("success", true);
            jsonObject.put("mgs", "");
            request.getSession().setAttribute("user", user);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            jsonObject.put("success", false);
            jsonObject.put("mgs", message);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = {"/all.action"})
    @ResponseBody
    public List<User> queryAll(){
        List<User> users = userService.queryAll();
        return users;
    }

}
