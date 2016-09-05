package com.yimeng.library.pickerview.bean;

import java.util.List;

/**
 * 获取省市区 - 省信息
 */
public class ProvinceBean {
    private String name;
    private List<CityBean> cityList;

    public ProvinceBean() {
        super();
    }

    public ProvinceBean(String name, List<CityBean> cityList) {
        super();
        this.name = name;
        this.cityList = cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityBean> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "ProvinceBean [name=" + name + ", cityList=" + cityList + "]";
    }

}
