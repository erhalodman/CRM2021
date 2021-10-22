package com.erha.CRM.web.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassNameLoginInterceptor
 * @Description TODO 登录拦截器
 * @Author DELL
 * @Date 2021/10/1116:27
 * @Version 1.0
 **/
public class  LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=======================>进入拦截器<==========================");
        Object user = request.getSession().getAttribute("user");
        StringBuffer requestURL = request.getRequestURL();
        if(user == null && requestURL.indexOf("/login.action")<0){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
