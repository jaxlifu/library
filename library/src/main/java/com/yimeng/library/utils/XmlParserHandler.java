package com.yimeng.library.utils;


import com.yimeng.library.pickerview.bean.AreaBean;
import com.yimeng.library.pickerview.bean.CityBean;
import com.yimeng.library.pickerview.bean.ProvinceBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserHandler extends DefaultHandler {
    /**
     * 存储所有的解析对象
     */
    private List<ProvinceBean> provinceList = new ArrayList<>();

    public XmlParserHandler() {

    }

    public List<ProvinceBean> getDataList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {
        // 当读到第一个开始标签的时候，会触发这个方法
    }

    ProvinceBean mProvinceBean = new ProvinceBean();
    CityBean mCityBean = new CityBean();
    AreaBean mAreaBean = new AreaBean();

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // 当遇到开始标记的时候，调用这个方法
        switch (qName) {
            case "province":
                mProvinceBean = new ProvinceBean();
                mProvinceBean.setName(attributes.getValue(0));
                mProvinceBean.setCityList(new ArrayList<CityBean>());
                break;
            case "city":
                mCityBean = new CityBean();
                mCityBean.setName(attributes.getValue(0));
                mCityBean.setDistrictList(new ArrayList<AreaBean>());
                break;
            case "district":
                mAreaBean = new AreaBean();
                mAreaBean.setName(attributes.getValue(0));
                mAreaBean.setAreaId(attributes.getValue(1));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // 遇到结束标记的时候，会调用这个方法
        switch (qName) {
            case "district":
                mCityBean.getDistrictList().add(mAreaBean);
                break;
            case "city":
                mProvinceBean.getCityList().add(mCityBean);
                break;
            case "province":
                provinceList.add(mProvinceBean);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
