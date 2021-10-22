package com.erha.CRM.workbench.web.controller;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.settings.service.UserService;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.util.UUIDUtil;
import com.erha.CRM.workbench.domain.Activity;
import com.erha.CRM.workbench.domain.ActivityRemark;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.service.ActivitService;
import com.erha.CRM.workbench.service.ActivityRemarkService;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameActivityController
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1214:43
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = {"/activity"})
public class ActivityController {
    @Autowired
    private ActivitService activitService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/save.action"})
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        User user = (User) request.getSession().getAttribute("user");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(user.getName());
        boolean b = activitService.saveActivit(activity);
        System.out.println("=========================>"+b);
        map.put("success", b);
        return map;
    }

    @RequestMapping(value = {"/pageInfo.action"})
    @ResponseBody
    public PageInfo<ActivityUserVo> queryPageInfo(String pageNo,String pageSize,Activity activity){
        PageInfo<ActivityUserVo> pageInfo = activitService.queryActivityPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize), activity);
        return pageInfo;
    }

    @RequestMapping(value = {"/queryByID.action"})
    @ResponseBody
    public Activity queryByID(String id){
        Activity activity = activitService.queryActivityById(id);
        return activity;
    }

    @RequestMapping(value = {"/update.action"})
    @ResponseBody
    public Boolean updateActivity(Activity activity,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        activity.setEditBy(user.getName());
        activity.setEditTime(DateTimeUtil.getSysTime());
        return activitService.updateActivity(activity);
    }

    @RequestMapping(value = {"/delete.action"})
    @ResponseBody
    public Boolean deleteActivity(String ids){
        return activitService.deleteActivity(ids);
    }

    @RequestMapping(value = {"/deleteDoIndex.action"})
    public ModelAndView deleteActivityDoIndex(String ids){
        ModelAndView modelAndView = new ModelAndView();
        boolean b = activitService.deleteActivity(ids);
        modelAndView.addObject("success", b);
        modelAndView.setViewName("redirect:/workbench/activity/index.jsp");
        return modelAndView;
    }

    @RequestMapping(value = {"/doDetail.action"})
    public ModelAndView doDetail(String id){
        ModelAndView modelAndView = new ModelAndView();
        Activity activity = activitService.queryActivityById(id);
        String owner = activity.getOwner();
        User user = userService.queryById(owner);
        modelAndView.addObject("activity",activity);
        modelAndView.addObject("ownerName", user.getName());
        modelAndView.setViewName("forward:/workbench/activity/detail.jsp");
        return modelAndView;
    }

    @RequestMapping(value = {"/queryPageActivitys.action"})
    @ResponseBody
    public PageInfo<Activity> queryPageActivitys(String pageNo,String pageSize){
        PageInfo<Activity> pageInfo = activitService.queryActivitySPageInfo(pageNo, pageSize);
        return pageInfo;
    }
}
