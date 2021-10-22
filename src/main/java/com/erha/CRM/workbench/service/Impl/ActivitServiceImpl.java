package com.erha.CRM.workbench.service.Impl;

import com.erha.CRM.util.TransactionInvocationHandler;
import com.erha.CRM.workbench.dao.ActivityDao;
import com.erha.CRM.workbench.domain.Activity;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.service.ActivitService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassNameActivitServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1214:36
 * @Version 1.0
 **/
@Service
public class ActivitServiceImpl implements ActivitService {
    @Autowired
    private ActivityDao activityDao;

    @Override
    public boolean saveActivit(Activity activity) {
        Integer integer = activityDao.insertActivity(activity);
        if(integer > 0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<ActivityUserVo> queryActivityPage(Integer pageNo, Integer pageSize, Activity activity) {
        PageHelper.startPage(pageNo, pageSize);
        List<ActivityUserVo> activities = activityDao.selectActivities(activity);
        PageInfo<ActivityUserVo> pageInfo = new PageInfo<>(activities);
        return pageInfo;
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityDao.selectActivityById(id);
    }

    @Override
    public boolean updateActivity(Activity activity) {
        Integer integer = activityDao.updateActivity(activity);
        if(integer>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteActivity(String ids) {
        String[] split = ids.split("/");
        int num = 0;
        for (String id : split){
            if(activityDao.deleteActivity(id) > 0){
                num++;
            }
        }
        if(num == split.length){
            return true;
        }
        throw new RuntimeException("多条删除失败");
    }

    @Override
    public PageInfo<Activity> queryActivitySPageInfo(String pageNo, String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        List<Activity> activities = activityDao.selectActivityS();
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return pageInfo;
    }
}
