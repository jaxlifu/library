package com.yimeng.library.pickerview;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.yimeng.library.R;
import com.yimeng.library.utils.XmlParserHandler;
import com.yimeng.library.pickerview.bean.AreaBean;
import com.yimeng.library.pickerview.bean.CityBean;
import com.yimeng.library.pickerview.bean.ProvinceBean;
import com.yimeng.library.pickerview.lib.ArrayWheelAdapter;
import com.yimeng.library.pickerview.lib.OnWheelChangedListener;
import com.yimeng.library.pickerview.lib.ScreenInfo;
import com.yimeng.library.pickerview.lib.WheelView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AddressPopWindow implements OnWheelChangedListener, OnClickListener, OnDismissListener {
    /**
     * 滚轮省
     */
    private WheelView mViewProvince;
    /**
     * 滚轮市
     */
    private WheelView mViewCity;
    /**
     * 滚轮区
     */
    private WheelView mViewDistrict;
    /**
     * 确定
     */
    private TextView m_tvMakeSure;
    /**
     * 取消
     */
    private TextView m_tvCancel;
    /**
     * 上下文
     */
    private Context context;

    /**
     * 省市区联动选择监听器
     */
    private OnAddressSelectedListener mOnAddressSelectedListener;

    /**
     * 所有省份
     */
    private String[] mProvinceData;
    /**
     * key - 省 value - 市
     */
    private Map<String, String[]> mCityDataMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    private Map<String, String[]> mDistrictDataMap = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
    private Map<String, String> mZipCodeDataMap = new HashMap<>();
    /**
     * 当前省的名称
     */
    private String mCurrentProvinceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentDistrictName = "";

    private String mAreaId = "";

    private PopupWindow popupWindow;

    private int mScreenHigh;

    /**
     * 构造
     */
    public AddressPopWindow(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pw_options, null);

        mViewProvince = (WheelView) view.findViewById(R.id.options1);
        mViewCity = (WheelView) view.findViewById(R.id.options2);
        mViewDistrict = (WheelView) view.findViewById(R.id.options3);
        view.findViewById(R.id.options4).setVisibility(View.GONE);
        m_tvCancel = (TextView) view.findViewById(R.id.btnCancel);
        m_tvMakeSure = (TextView) view.findViewById(R.id.btnSubmit);
        ScreenInfo screenInfo = new ScreenInfo((Activity) context);
        mScreenHigh = screenInfo.getHeight();
        setUpListener();
        setUpData();

        initPop(view);
    }

    /**
     * 初始化popwindow
     */
    private void initPop(View view) {
        popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        //设置popwindow的动画效果
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听
    }

    /**
     * 设置监听
     */
    private void setUpListener() {
        // TODO Auto-generated method stub
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        m_tvMakeSure.setOnClickListener(this);
        m_tvCancel.setOnClickListener(this);
    }

    /**
     * 添加数据
     */
    private void setUpData() {
        initProvinceData();
        mViewProvince.setAdapter(new ArrayWheelAdapter<>(Arrays.asList(mProvinceData)));

        // 设置可见条目数量
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (mScreenHigh / 200) * 5;
        mViewProvince.TEXT_SIZE = textSize;
        mViewProvince.setVisibleItems(5);
        mViewProvince.setCurrentItem(0);

        mViewCity.setVisibleItems(5);
        mViewCity.TEXT_SIZE = textSize;
        mViewProvince.setCurrentItem(0);

        mViewDistrict.setVisibleItems(5);
        mViewDistrict.TEXT_SIZE = textSize;
        mViewProvince.setCurrentItem(0);
        updateCities();
        updateAreas();
    }

    /**
     * 初始化整个控件,通过xml解析assets目录下文件,将数据初始化
     */
    private void initProvinceData() {
        List<ProvinceBean> provinceList;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            /* 创建一个解析xml的工厂对象*/
            SAXParserFactory spf = SAXParserFactory.newInstance();
            /* 解析xml*/
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close(); /* 获取解析出来的数据*/
            provinceList = handler.getDataList(); /**/
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProvinceName = provinceList.get(0).getName();
                List<CityBean> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<AreaBean> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mAreaId = districtList.get(0).getAreaId();
                }
            }
            if (provinceList == null || provinceList.size() == 0) return;
            mProvinceData = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                /* 遍历所有省的数据*/
                mProvinceData[i] = provinceList.get(i).getName();
                List<CityBean> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    /* 遍历省下面的所有市的数据*/
                    cityNames[j] = cityList.get(j).getName();
                    List<AreaBean> districtList = cityList.get(j).getDistrictList();
                    String[] districtNameArray = new String[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        /* 遍历市下面所有区/县的数据*/
                        AreaBean areaBean = new AreaBean(districtList.get(k).getName(), districtList.get(k).getAreaId());
                        /* 区/县对于的邮编，保存到mZipCodeDataMap*/
                        mZipCodeDataMap.put(cityList.get(j).getName() + districtList.get(k).getName(), districtList.get(k).getAreaId());
                        districtNameArray[k] = areaBean.getName();
                    }
                    /* 市-区/县的数据，保存到mDistrictDataMap*/
                    mDistrictDataMap.put(cityNames[j], districtNameArray);
                }
                /* 省-市的数据，保存到mCityDataMap*/
                mCityDataMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSubmit) {
            mOnAddressSelectedListener.onProvincesSelected(mCurrentProvinceName, mCurrentCityName, mCurrentDistrictName, mAreaId);
            dismiss();
        } else if (i == R.id.btnCancel) {
            dismiss();
        }
    }


    /**
     * 更新市
     */
    public void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProvinceName = mProvinceData[pCurrent];
        String[] cities = mCityDataMap.get(mCurrentProvinceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setAdapter(new ArrayWheelAdapter<>(Arrays.asList(cities)));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 更新地区
     */
    public void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCityDataMap.get(mCurrentProvinceName)[pCurrent];
        String[] areas = mDistrictDataMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setAdapter(new ArrayWheelAdapter<>(Arrays.asList(areas)));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];
    }

    @Override
    public void onDismiss() {
    }


    /**
     * 消除弹窗
     */
    public void dismiss() {
        popupWindow.dismiss();
    }


    /**
     * 弹窗显示的位置
     */
    public void showAsDropDown(View parent) {
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDataMap.get(mCurrentCityName)[newValue];
        }
        mAreaId = mZipCodeDataMap.get(mCurrentCityName + mCurrentDistrictName);
    }

    /**
     * 省市区联动监听器
     */
    public interface OnAddressSelectedListener {
        void onProvincesSelected(String province, String city, String district, String areaId);
    }

    /**
     * 添加监听
     */
    public void setOnAddressSelectedListener(OnAddressSelectedListener mOnAddressSelectedListener) {
        this.mOnAddressSelectedListener = mOnAddressSelectedListener;
    }
}
