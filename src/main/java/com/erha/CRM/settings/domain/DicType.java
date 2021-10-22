package com.erha.CRM.settings.domain;

/**
 * @ClassNameDicType
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1510:30
 * @Version 1.0
 **/
public class DicType {
    private String code;
    private String name;
    private String description;

    public DicType() {
    }

    @Override
    public String toString() {
        return "DicType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
