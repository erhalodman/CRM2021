package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkDao {
    Integer insertActivityRemark(ActivityRemark activityRemark);

    List<ActivityRemark> selectPageInfoByid(@Param("activityId") String activityId);

    ActivityRemark selectById(@Param("id") String id);

    Integer updateRemark(ActivityRemark activityRemark);

    Integer deleteRemark(@Param("id") String id);
}
