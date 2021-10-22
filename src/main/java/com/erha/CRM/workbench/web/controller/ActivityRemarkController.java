package com.erha.CRM.workbench.web.controller;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.util.UUIDUtil;
import com.erha.CRM.workbench.domain.ActivityRemark;
import com.erha.CRM.workbench.service.ActivityRemarkService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassNameActivityRemarkController
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1216:20
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = {"/activityRemark"})
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping(value = {"/save.action"})
    @ResponseBody
    public Boolean createActivityRemark(ActivityRemark activityRemark, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setOwner(user.getId());
        activityRemark.setCreateBy(user.getName());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        return activityRemarkService.createActivityRemark(activityRemark);
    }

    @RequestMapping(value = {"/pageInfoRemark.action"})
    @ResponseBody
    public PageInfo<ActivityRemark> pageInfoRemark(String pageNo,String pageSize,String activityId){
        PageInfo<ActivityRemark> pageInfo = activityRemarkService.queryActivityRemarkPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize),activityId);
        return pageInfo;
    }

    @RequestMapping(value = {"/queryById.action"},method = RequestMethod.POST)
    @ResponseBody
    public ActivityRemark queryById(String id){
        return activityRemarkService.queryById(id);
    }

    @RequestMapping(value = {"/updateRemark.action"},method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateRemark(ActivityRemark activityRemark,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        activityRemark.setEditFlag("1");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        return activityRemarkService.updateRemark(activityRemark);
    }

    @RequestMapping(value = {"/deleteRemark.action"})
    @ResponseBody
    public Boolean deleteRemark(String id){
        return activityRemarkService.deleteRemark(id);
    }

}
