package com.erha.CRM.workbench.service;

import com.erha.CRM.workbench.domain.ActivityRemark;
import com.github.pagehelper.PageInfo;

public interface ActivityRemarkService {
    boolean createActivityRemark(ActivityRemark activityRemark);

    PageInfo<ActivityRemark> queryActivityRemarkPage(Integer pageNo,Integer pageSize,String activityId);

    ActivityRemark queryById(String id);

    Boolean updateRemark(ActivityRemark activityRemark);

    Boolean deleteRemark(String id);
}
