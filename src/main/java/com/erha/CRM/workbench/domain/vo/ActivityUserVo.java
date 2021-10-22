package com.erha.CRM.workbench.domain.vo;

/**
 * @ClassNameActivityUserVo
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1311:58
 * @Version 1.0
 **/
public class ActivityUserVo {
    private String id;
    private String userName;
    private String activityName;
    private String startDate;
    private String endDate;

    public ActivityUserVo() {
    }

    @Override
    public String toString() {
        return "ActivityUserVo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", activityName='" + activityName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
