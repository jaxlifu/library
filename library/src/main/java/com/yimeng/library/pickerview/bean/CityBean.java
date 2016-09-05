package com.yimeng.library.pickerview.bean;

import java.util.List;

/**
 * 获取省市区 - 市信息
 */
public class CityBean {
    private String name;
    private int id;
    private String first;
    private List<AreaBean> districtList;

    public CityBean() {
        super();
    }

    public CityBean(String name, List<AreaBean> districtList) {
        super();
        this.name = name;
        this.districtList = districtList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaBean> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<AreaBean> districtList) {
        this.districtList = districtList;
    }

    @Override
    public String toString() {
        return "CityBean [name=" + name + ", districtList=" + districtList + "]";
    }

}
