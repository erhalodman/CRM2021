package com.erha.CRM.workbench.domain.vo;

/**
 * @ClassNameClueUserVo
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1519:14
 * @Version 1.0
 **/
public class ClueUserVo {
    private String id;	//主键
    private String fullname;	//全名（人的名字）
    private String appellation;	//称呼
    private String owner;	//所有者
    private String company;	//公司名称
    private String phone;	//公司电话
    private String mphone;	//手机
    private String state;	//状态
    private String source;	//来源
    private String userName; //所有者名称

    public ClueUserVo() {
    }

    @Override
    public String toString() {
        return "ClueUserVo{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", appellation='" + appellation + '\'' +
                ", owner='" + owner + '\'' +
                ", company='" + company + '\'' +
                ", phone='" + phone + '\'' +
                ", mphone='" + mphone + '\'' +
                ", state='" + state + '\'' +
                ", source='" + source + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
