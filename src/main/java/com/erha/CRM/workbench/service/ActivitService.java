package com.erha.CRM.workbench.service;

import com.erha.CRM.workbench.domain.Activity;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.github.pagehelper.PageInfo;

public interface ActivitService {

    boolean saveActivit(Activity activity);

    PageInfo<ActivityUserVo> queryActivityPage(Integer pageNo, Integer pageSize, Activity activity);

    Activity queryActivityById(String id);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(String ids);

    PageInfo<Activity> queryActivitySPageInfo(String pageNo,String pageSize);
}
