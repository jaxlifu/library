package com.yimeng.library.pickerview.bean;

/**
 * 获取省市区 - 区信息
 */
public class AreaBean {
    private String name;
    private String area_id;

    public AreaBean() {
        super();
    }

    public AreaBean(String name, String area_id) {
        super();
        this.name = name;
        this.area_id = area_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return area_id;
    }

    public void setAreaId(String area_id) {
        this.area_id = area_id;
    }
}
