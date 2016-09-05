package com.yimeng.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.yimeng.library.pickerview.AddressPopWindow;
import com.yimeng.library.pickerview.OptionsPopupWindow;
import com.yimeng.library.pickerview.TimePopupWindow;

import java.util.ArrayList;

/**
 * 底部选择弹窗的工具类封装
 * Created by jax on 16/1/30.
 */
public class OptionsUtils {
    /**
     * 选择性别的弹窗部分
     */
    public static void showGardenSelect(Context context, View view, OptionsPopupWindow.OnOptionsSelectListener listener) {
        OptionsPopupWindow picker = new OptionsPopupWindow(context);
        final ArrayList<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        list.add("保密");
        picker.setPicker(list);
        picker.setOnOptionsSelectListener(listener);
        picker.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择生日
     */
    public static void showDateSelect(Context context, View view, TimePopupWindow.OnTimeSelectListener listener) {
        TimePopupWindow pop = new TimePopupWindow(context, TimePopupWindow.Type.YEAR_MONTH_DAY);
        pop.setOnTimeSelectListener(listener);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择身高
     */
    public static void showheight(Context context, View view, OptionsPopupWindow.OnOptionsSelectListener listener) {
        OptionsPopupWindow picker = new OptionsPopupWindow(context);
        picker.setPicker(getData(0));
        picker.setSelectOptions(160);
        picker.setLabels("身高");
        picker.setOnOptionsSelectListener(listener);
        picker.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择体重
     */
    public static void showWeight(Context context, View view, OptionsPopupWindow.OnOptionsSelectListener listener) {
        OptionsPopupWindow picker = new OptionsPopupWindow(context);
        picker.setPicker(getData(1));
        picker.setSelectOptions(50);
        picker.setLabels("体重");
        picker.setOnOptionsSelectListener(listener);
        picker.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择体重
     */
    public static void showShencai(Context context, View view, OptionsPopupWindow.OnOptionsSelectListener listener) {
        OptionsPopupWindow picker = new OptionsPopupWindow(context);
        picker.setPicker(getData(2), getData(3), getData(4), getData(5), false);
        picker.setSelectOptions(1, 90, 70, 80);
        picker.setLabels("罩杯", "胸围", "腰围", "臀围");
        picker.setOnOptionsSelectListener(listener);
        picker.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public static void showAddress(Context context, View view, AddressPopWindow.OnAddressSelectedListener listener) {
        AddressPopWindow picker = new AddressPopWindow(context);
        picker.setOnAddressSelectedListener(listener);
        picker.showAsDropDown(view);
    }

    /**
     * @return 获取数据
     */
    private static ArrayList<String> getData(int type) {
        ArrayList<String> list = new ArrayList<>();
        if (type == 0) for (int i = 0; i < 230; i++) list.add(i + "cm");
        else if (type == 1) for (int i = 0; i < 150; i++) list.add(i + "kg");
        else if (type == 2) for (int i = 0; i < 8; i++) list.add(transForCup(i));
        else if (type == 3) for (int i = 0; i < 150; i++) list.add(i + "");
        else if (type == 4) for (int i = 0; i < 150; i++) list.add(i + "");
        else if (type == 5) for (int i = 0; i < 150; i++) list.add(i + "");
        return list;
    }


    /**
     * 转化胸围
     */
    private static String transForCup(int i) {
        String cup = "";
        if (i == 0) cup = "A";
        else if (i == 1) cup = "B";
        else if (i == 2) cup = "C";
        else if (i == 3) cup = "D";
        else if (i == 4) cup = "E";
        else if (i == 5) cup = "F";
        else if (i == 6) cup = "G";
        else if (i == 7) cup = "H";
        return cup;
    }

    /**
     * @param sex 性别id
     * @return 性别字符
     */
    public static String getSexValue(int sex) {
        if (sex == 1) return "男";
        if (sex == 2) return "女";
        return "保密";
    }
}
