package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.Activity;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {
    Integer insertActivity(Activity activity);

    Integer updateActivity(Activity activity);

    Integer deleteActivity(@Param("id") String id);

    List<ActivityUserVo> selectActivities(Activity activity);

    Activity selectActivityById(@Param("id") String id);

    List<Activity> selectActivityS();





}
