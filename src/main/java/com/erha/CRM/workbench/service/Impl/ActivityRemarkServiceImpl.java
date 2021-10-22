package com.erha.CRM.workbench.service.Impl;

import com.erha.CRM.workbench.dao.ActivityRemarkDao;
import com.erha.CRM.workbench.domain.ActivityRemark;
import com.erha.CRM.workbench.service.ActivityRemarkService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassNameActivityRemarkServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1216:18
 * @Version 1.0
 **/
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public boolean createActivityRemark(ActivityRemark activityRemark) {
        Integer integer = activityRemarkDao.insertActivityRemark(activityRemark);
        if(integer>0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<ActivityRemark> queryActivityRemarkPage(Integer pageNo, Integer pageSize,String activityId) {
        PageHelper.startPage(pageNo,pageSize);
        List<ActivityRemark> activityRemarks = activityRemarkDao.selectPageInfoByid(activityId);
        PageInfo<ActivityRemark> pageInfo = new PageInfo<>(activityRemarks);
        return pageInfo;
    }

    @Override
    public ActivityRemark queryById(String id) {
        return activityRemarkDao.selectById(id);
    }

    @Override
    public Boolean updateRemark(ActivityRemark activityRemark) {
        Integer integer = activityRemarkDao.updateRemark(activityRemark);
        if(integer > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteRemark(String id) {
        Integer integer = activityRemarkDao.deleteRemark(id);
        if(integer > 0){
            return true;
        }
        return false;
    }


}
